package com.jys.catsapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.jys.catsapp.core.generic.usecase.BaseUseCase
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.QUERY_CAT
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.domain.model.toDomain
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class GetCatUseCase(
    private val repository: PexelPagingRepositoryInterface
) : BaseUseCase<Unit, Flow<PagingData<Photo>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<Photo>> {
        return repository
            .getPhotos(QUERY_CAT)
            .buffer(
                capacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST
            )
    }
}

class GetCatUseCaseWithRoom(
    private val repository: PexelPagingRepositoryInterface
) : BaseUseCase<Unit, Flow<PagingData<PhotoDomain>>> {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(input: Unit): Flow<PagingData<PhotoDomain>> {
        
        return repository
            .getPhotosWithRoom(QUERY_CAT)
            .map{ pagingData -> pagingData.map { it.toDomain() } }
    }
}

