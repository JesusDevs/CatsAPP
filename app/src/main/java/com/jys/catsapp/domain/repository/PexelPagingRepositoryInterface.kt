package com.jys.catsapp.domain.repository

import androidx.paging.PagingData
import com.jys.catsapp.data.localDB.PhotoEntity
import com.jys.catsapp.data.network.model.Photo
import kotlinx.coroutines.flow.Flow

interface PexelPagingRepositoryInterface {
    fun getPhotos(query: String): Flow<PagingData<Photo>>
    fun getPhotosWithRoom(query: String): Flow<PagingData<PhotoEntity>>
}



