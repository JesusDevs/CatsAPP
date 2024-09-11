package com.jys.catsapp.core.common.generic.error

suspend fun <T> safeApiCall(call: suspend () -> retrofit2.Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            Result.Success(data = response.body()!!)
        } else {
            Result.Failure(exception = Exception(response.errorBody()?.string() ?: "Unknown Error"))
        }
    }catch (e: Exception) {
        Result.Failure(exception = e)
    } catch (e: Throwable) {
        Result.Failure(exception = e)
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}
