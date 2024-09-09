package com.jys.catsapp.di

import com.jys.catsapp.BuildConfig
import com.jys.catsapp.core.utils.ConstantsUtil.PagingConstants.PAGING_3_SERVICE
import com.jys.catsapp.core.utils.ConstantsUtil.ServiceConstants.URL_PEXEL
import com.jys.catsapp.data.network.PexelsApiService
import com.jys.catsapp.data.network.RetrofitObject
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single<Retrofit>(named(PAGING_3_SERVICE)) {
        RetrofitObject.create(URL_PEXEL, BuildConfig.API_KEY_PEXEL)
    }

    single<PexelsApiService> {
        get<Retrofit>(named(PAGING_3_SERVICE)).create(PexelsApiService::class.java)
    }
}