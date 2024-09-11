package com.jys.catsapp.core.common.generic.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}