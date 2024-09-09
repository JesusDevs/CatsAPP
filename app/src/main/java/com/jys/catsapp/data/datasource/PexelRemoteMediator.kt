package com.jys.catsapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jys.catsapp.data.localDB.CatsDatabase
import com.jys.catsapp.data.localDB.PhotoDao
import com.jys.catsapp.data.localDB.PhotoEntity
import com.jys.catsapp.data.localDB.PhotoRemoteKey
import com.jys.catsapp.data.localDB.PhotoRemoteKeyDao
import com.jys.catsapp.data.mapper.toEntity
import com.jys.catsapp.data.network.PexelsApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PexelRemoteMediator(
    private val query: String,
    private val apiService: PexelsApiService,
    private val photoDao: PhotoDao,
    private val photoRemoteKeyDao: PhotoRemoteKeyDao,
    private val catsDb: CatsDatabase
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun initialize(): InitializeAction {
        println("Initialize RemoteMediator")
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        try {
            // Determinar la clave de paginación
            val pageKey = when (loadType) {
                LoadType.REFRESH -> {
                    println("LoadType: REFRESH")
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    println("RemoteKey closest to current position: $remoteKey")
                    remoteKey?.nextPageKey ?: 1
                }
                LoadType.PREPEND -> {
                    println("LoadType: PREPEND")
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    println("RemoteKey for first item: $remoteKey")
                    val prevKey = remoteKey?.prevPageKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    println("Previous key: $prevKey")
                    prevKey
                }
                LoadType.APPEND -> {
                    println("LoadType: APPEND")
                    val remoteKey = getRemoteKeyForLastItem(state)
                    println("RemoteKey for last item: $remoteKey")
                    val nextKey = remoteKey?.nextPageKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    println("Next key: $nextKey")
                    nextKey
                }
            }

            println("Fetching page with key: $pageKey")

            // Llamada a la API
            val apiResponse = apiService.searchPhotos(query, state.config.pageSize, pageKey)
            val photos = apiResponse.body()?.photos?.mapIndexed { index, photoDto ->
                photoDto?.toEntity(pageKey)?.copy(position = index)
            } ?: emptyList()

            println("API Response photos: $photos")

            val nextPage = extractPageNumber(apiResponse.body()?.nextPage)
            val prevPage = extractPageNumber(apiResponse.body()?.prevPage)
            val endOfPaginationReached = nextPage == null

            println("Pagination: nextPage = $nextPage, prevPage = $prevPage, endOfPaginationReached = $endOfPaginationReached")

            catsDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    println("Clearing data for refresh")
                    photoDao.clearAll() // Limpiar la tabla de fotos
                    photoRemoteKeyDao.clearRemoteKeys() // Limpiar las claves
                }

                // Insertar nuevas fotos
                if (photos.isNotEmpty()) {
                    println("Inserting photos into DB")
                    photoDao.insertAll(photos.filterNotNull())
                }

                // Guardar clave de paginación para el primer ítem
                photos.firstOrNull()?.let { firstPhoto ->
                    println("Saving remote keys for first photo id: ${firstPhoto.id}")
                    photoRemoteKeyDao.insertOrReplace(
                        PhotoRemoteKey(
                            photoId = (firstPhoto.id ?: 0).toString(),
                            prevPageKey = prevPage,  // clave de la página anterior
                            nextPageKey = nextPage   // clave de la siguiente página
                        )
                    )
                }

                // Guardar clave de paginación para el último ítem
                photos.lastOrNull()?.let { lastPhoto ->
                    println("Saving remote keys for last photo id: ${lastPhoto.id}")
                    photoRemoteKeyDao.insertOrReplace(
                        PhotoRemoteKey(
                            photoId = (lastPhoto.id ?: 0).toString(),
                            prevPageKey = prevPage,
                            nextPageKey = nextPage
                        )
                    )
                }
            }


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            println("Error: IOException $exception")
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            println("Error: HttpException $exception")
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PhotoEntity>): PhotoRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()?.let { photo ->
                println("Fetching remote key for last item: ${photo.id}")
                photoRemoteKeyDao.remoteKeyByPhoto(photo.id.toString())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PhotoEntity>): PhotoRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { photo ->
                println("Fetching remote key for first item: ${photo.id}")
                photoRemoteKeyDao.remoteKeyByPhoto(photo.id.toString())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PhotoEntity>): PhotoRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                println("Fetching remote key for anchor position: $id")
                photoRemoteKeyDao.remoteKeyByPhoto(id.toString())
            }
        }
    }

    // Extraer el número de página desde la URL
    private fun extractPageNumber(pageUrl: String?): Int? {
        return pageUrl?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()
    }
}

