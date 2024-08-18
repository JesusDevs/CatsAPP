package com.jys.catsapp.domain.repository

import androidx.paging.PagingData
import com.jys.core.network.model.Photo
import kotlinx.coroutines.flow.Flow

interface PexelPagingRepositoryInterface {
    fun getPhotos(query: String): Flow<PagingData<Photo>>
}



