package com.jys.catsapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.jys.catsapp.core.generic.usecase.BaseUseCase
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.QUERY_CAT
import com.jys.catsapp.domain.model.PhotoDomain
import com.jys.catsapp.domain.model.toDomain
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCatUseCaseWithRoom(
    private val repository: PexelPagingRepositoryInterface
) : BaseUseCase<Unit, Flow<PagingData<PhotoDomain>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<PhotoDomain>> {

        return repository
            .getPhotosWithRoom(QUERY_CAT)
            .flowOn(Dispatchers.IO)
            .map{ pagingData -> pagingData.map { it.toDomain() } }
    }
}