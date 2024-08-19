package com.jys.catsapp.core.generic.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}