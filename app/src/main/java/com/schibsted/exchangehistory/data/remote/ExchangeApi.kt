package com.schibsted.exchangehistory.data.remote

import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {
    @GET("history")
    fun getHistory(
        @Query("start_at") start_at: String,
        @Query("end_at") end_at: String,
        @Query("base") base: String,
        @Query("symbols") to: String
    ): Deferred<Response<ExchangeHistoryResponse>>
}