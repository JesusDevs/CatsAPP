package com.jys.core.network

import com.jys.core.network.model.PexelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {

    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<PexelResponse>
}