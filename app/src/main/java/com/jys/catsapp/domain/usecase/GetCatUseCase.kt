package com.jys.catsapp.domain.usecase

import androidx.paging.PagingData
import com.jys.catsapp.core.common.generic.usecase.BaseUseCase
import com.jys.catsapp.core.common.utils.ConstantsUtil.PagingConstants.QUERY_CAT
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import kotlinx.coroutines.flow.Flow

class GetCatUseCase(
    private val repository: PexelPagingRepositoryInterface
) : BaseUseCase<Unit, Flow<PagingData<Photo>>> {

    override suspend fun execute(input: Unit): Flow<PagingData<Photo>> {
        return repository
            .getPhotos(QUERY_CAT)
    }
}



