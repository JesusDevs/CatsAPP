package com.jys.paging3.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jys.core.generic.error.Result
import com.jys.core.generic.error.safeApiCall
import com.jys.core.network.PexelsApiService
import com.jys.core.network.model.Photo
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

        return when (response) {
            is Result.Success -> {
                val data =  response.data.photos ?: emptyList()
                LoadResult.Page(
                    data = data.filterNotNull(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            }
            is Result.Failure -> {
                LoadResult.Error(response.exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }
}



