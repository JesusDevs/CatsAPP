package com.jys.catsapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jys.catsapp.data.database.CatsDatabase
import com.jys.catsapp.data.database.PhotoDao
import com.jys.catsapp.data.database.entity.PhotoEntity
import com.jys.catsapp.data.database.entity.PhotoRemoteKeyEntity
import com.jys.catsapp.data.database.PhotoRemoteKeyDao
import com.jys.catsapp.data.database.mapper.toEntity
import com.jys.catsapp.data.network.PexelsApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PexelRemoteMediator(
    private val query: String,
    private val apiService: PexelsApiService,
    private val catsDb: CatsDatabase
) : RemoteMediator<Int, PhotoEntity>() {

    private val photoDao: PhotoDao = catsDb.photoDao()
    private val photoRemoteKeyDao: PhotoRemoteKeyDao = catsDb.photoRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPageKey ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKey?.prevPageKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKey?.nextPageKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextKey
                }
            }


            val apiResponse = apiService.searchPhotos(query, state.config.pageSize, pageKey)
            val photos = apiResponse.body()?.photos?.mapIndexed { index, photoDto ->
                photoDto?.toEntity(pageKey)?.copy(position = index)
            } ?: emptyList()


            val nextPage = extractPageNumber(apiResponse.body()?.nextPage)
            val prevPage = extractPageNumber(apiResponse.body()?.prevPage)
            val endOfPaginationReached = (nextPage == null)

            println("Pagination: nextPage = $nextPage, prevPage = $prevPage, endOfPaginationReached = $endOfPaginationReached")

            catsDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.clearAll()
                    photoRemoteKeyDao.clearRemoteKeys()
                }

                if (photos.isNotEmpty()) {
                    photoDao.insertAll(photos.filterNotNull())
                }

                photos.firstOrNull()?.let { firstPhoto ->
                    photoRemoteKeyDao.insertOrReplace(
                        PhotoRemoteKeyEntity(
                            photoId = (firstPhoto.id ?: 0).toString(),
                            prevPageKey = prevPage,
                            nextPageKey = nextPage
                        )
                    )
                }
                photos.lastOrNull()?.let { lastPhoto ->
                    photoRemoteKeyDao.insertOrReplace(
                        PhotoRemoteKeyEntity(
                            photoId = (lastPhoto.id ?: 0).toString(),
                            prevPageKey = prevPage,
                            nextPageKey = nextPage
                        )
                    )
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PhotoEntity>): PhotoRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()?.let { photo ->
                println("Fetching remote key for last item: ${photo.id}")
                photoRemoteKeyDao.remoteKeyByPhoto(photo.id.toString())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PhotoEntity>): PhotoRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { photo ->
                println("Fetching remote key for first item: ${photo.id}")
                photoRemoteKeyDao.remoteKeyByPhoto(photo.id.toString())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PhotoEntity>): PhotoRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                println("Fetching remote key for anchor position: $id")
                photoRemoteKeyDao.remoteKeyByPhoto(id.toString())
            }
        }
    }

    private fun extractPageNumber(pageUrl: String?): Int? {
        return pageUrl?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()
    }
}

