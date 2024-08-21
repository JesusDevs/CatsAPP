package com.jys.catsapp.data.network

import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.AUTHORIZATION
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createRetrofit(baseUrl: String, authHeader: String? = null): Retrofit {
    val clientBuilder = OkHttpClient.Builder()

    authHeader?.let {
        clientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(AUTHORIZATION, it)
                    .build()
                chain.proceed(request)
            }
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())

        .client(clientBuilder.build())
        .build()
}