package com.jys.paging3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jys.core.network.PexelsApiService
import com.jys.core.network.model.Photo
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.PAGE_SIZE
import com.jys.paging3.data.datasource.PexelPagingDataSource
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import kotlinx.coroutines.flow.Flow

class PexelPagingRepository(
    private val apiService: PexelsApiService
) : PexelPagingRepositoryInterface {

    override fun getPhotos(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PexelPagingDataSource(apiService, query) }
        ).flow
    }
}


