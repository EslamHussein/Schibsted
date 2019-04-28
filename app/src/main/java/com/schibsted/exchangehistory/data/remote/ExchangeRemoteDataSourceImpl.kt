package com.schibsted.exchangehistory.data.remote

import com.google.gson.Gson
import com.schibsted.core.remote.dto.Result
import com.schibsted.core.remote.service.BaseRemoteRepo
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse

class ExchangeRemoteDataSourceImpl(private val exchangeApi: ExchangeApi, gson: Gson) : BaseRemoteRepo(gson),
    ExchangeRemoteDataSource {
    override suspend fun getHistory(
        start: String,
        end: String,
        base: String,
        to: String
    ): Result<ExchangeHistoryResponse> {
        return execute(exchangeApi.getHistory(start, end, base, to))
    }
}