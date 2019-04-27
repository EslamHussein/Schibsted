package com.schibsted.exchangehistory.repository

import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase

interface ExchangeRepository {
    suspend fun getHistory(params: GetExchangeHistoryUseCase.Params): Result<ExchangeHistoryResponse>
}