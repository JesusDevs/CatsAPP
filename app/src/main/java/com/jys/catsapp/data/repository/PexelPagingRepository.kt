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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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


    @OptIn(ExperimentalPagingApi::class, FlowPreview::class)
    override fun getPhotosWithRoom(query: String): Flow<PagingData<PhotoEntity>> {
        val pagingSourceFactory = { database.photoDao().getAllPhotos() }

        return Pager(
           config = PagingConfig(
                pageSize = 40,  // Tamaño de cada página de datos
                enablePlaceholders = true,  // Habilitar placeholders para cuando no haya datos
                prefetchDistance = 4,  // Realiza la carga cuando el usuario esté a 5 ítems del final
                initialLoadSize = 20,  // Tamaño de la carga inicial
                maxSize = 8000 // Máximo número de ítems a mantener en la memoria
            ),
            remoteMediator = PexelRemoteMediator(query, apiService, database.photoDao(),database.photoRemoteKeyDao() ,  database),
            pagingSourceFactory = pagingSourceFactory
        ).flow.distinctUntilChanged().debounce(1000)
    }
}



