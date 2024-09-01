package com.jys.catsapp.di

import com.jys.catsapp.data.datasource.PexelPagingDataSource
import com.jys.catsapp.data.repository.PexelPagingRepository
import com.jys.catsapp.domain.repository.PexelPagingRepositoryInterface
import com.jys.catsapp.domain.usecase.GetCatUseCase
import com.jys.catsapp.domain.usecase.GetCatUseCaseWithRoom
import com.jys.catsapp.presentation.HomeViewModel

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val homeModule = module {
        factoryOf(::PexelPagingDataSource)
        factoryOf(::GetCatUseCase)
        factoryOf(::GetCatUseCaseWithRoom)
        single<PexelPagingRepositoryInterface> { PexelPagingRepository(get(),get()) }
        viewModelOf(::HomeViewModel)
}