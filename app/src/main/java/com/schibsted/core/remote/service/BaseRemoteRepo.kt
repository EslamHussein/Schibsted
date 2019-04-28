package com.schibsted.core.remote.service

import com.google.gson.Gson
import com.schibsted.core.remote.dto.Result
import com.schibsted.core.remote.error.NetworkException
import kotlinx.coroutines.Deferred
import retrofit2.Response


abstract class BaseRemoteRepo(private val gson: Gson) {
    suspend fun <T : Any> execute(call: Deferred<Response<T>>): Result<T> {
        return try {
            val response = call.await()
            if (response.isSuccessful)
                Result.Success(response.body()!!)
            else
                Result.Error(gson.fromJson(response.errorBody()?.string(), NetworkException::class.java))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}