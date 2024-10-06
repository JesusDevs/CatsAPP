package com.jys.catsapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jys.catsapp.data.database.CatsDatabase
import com.jys.catsapp.data.database.PhotoDao
import com.jys.catsapp.data.database.entity.PhotoEntity
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

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        return try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {

                    val lastItem = state.pages.lastOrNull()?.data?.lastOrNull()
                    val lastKey = lastItem?.pageNumber?:1
                    if (state.pages.isEmpty() ) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    lastKey + 1
                }
            }
            val response = apiService.searchPhotos(query, state.config.pageSize, pageKey)


            val photos = response.body()?.photos?.map { photoList ->
                photoList?.toEntity(pageKey)
            }

            catsDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.clearAll()
                }
                photos?.filterNotNull()?.let { photoDao.insertAll(it) }
            }

            MediatorResult.Success(endOfPaginationReached = response.body()?.nextPage == null || response.body()?.photos.isNullOrEmpty() )
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}

