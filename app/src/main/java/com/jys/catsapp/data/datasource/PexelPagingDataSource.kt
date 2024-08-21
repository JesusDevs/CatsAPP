package com.jys.catsapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jys.catsapp.core.generic.error.Result
import com.jys.catsapp.core.generic.error.safeApiCall
import com.jys.catsapp.data.network.PexelsApiService
import com.jys.catsapp.data.network.model.Photo
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.PAGE_SIZE

class PexelPagingDataSource(
    private val apiService: PexelsApiService,
    private val query: String
) :PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        val currentPage = params.key ?: 1
        val response = safeApiCall {
            apiService.searchPhotos(
                query = query,
                page = currentPage,
                perPage = PAGE_SIZE
            )
        }
        when (response) {
            is Result.Success -> {
                val data =  response.data.photos ?: emptyList()
                return LoadResult.Page(
                    data = data.filterNotNull(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            }
            is Result.Failure -> {
                return LoadResult.Error(response.exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        state.closestItemToPosition(0)?.let {
            return state.anchorPosition
        }
        state.closestItemToPosition(state.pages.size - 1)?.let {
            return state.anchorPosition
        }
        return state.anchorPosition
    }
}



