package com.jys.catsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jys.catsapp.data.network.PexelsApiService
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.PAGE_SIZE
import com.jys.catsapp.data.datasource.PexelPagingDataSource
import com.jys.catsapp.data.datasource.PexelRemoteMediator
import com.jys.catsapp.data.localDB.CatsDatabase
import com.jys.catsapp.data.localDB.PhotoEntity
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PexelPagingRepository(
    private val apiService: PexelsApiService,
    private val database: CatsDatabase
) : PexelPagingRepositoryInterface {

    override fun getPhotos(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PexelPagingDataSource(apiService, query) }
        ).flow
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getPhotosWithRoom(query: String): Flow<PagingData<PhotoEntity>> {
        val pagingSourceFactory = { database.photoDao().getAllPhotos() }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = PexelRemoteMediator(query, apiService, database.photoDao()),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}



