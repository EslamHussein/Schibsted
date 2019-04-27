package com.schibsted.exchangehistory.data.remote

import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse

interface ExchangeRemoteDataSource {
    suspend fun getHistory(start: String, end: String, base: String, to: String): Result<ExchangeHistoryResponse>
}