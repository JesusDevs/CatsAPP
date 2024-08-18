package com.jys.core.generic.error

import android.util.Log

suspend fun <T> safeApiCall(call: suspend () -> retrofit2.Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            Result.Success(response.body()!!)
        } else {
            Log.d("Error", response.errorBody()?.string() ?: "Unknown Error")
            Result.Failure(Exception(response.errorBody()?.string() ?: "Unknown Error"))

        }
    } catch (e: Exception) {
        Log.d("Error", e.message?: "Unknown Error")
        Result.Failure(e)
    }
}
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}
