package com.schibsted.exchangehistory.repository

import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.data.remote.ExchangeRemoteDataSource
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase

class ExchangeRepositoryImpl(private val remote: ExchangeRemoteDataSource) : ExchangeRepository {
    override suspend fun getHistory(params: GetExchangeHistoryUseCase.Params): Result<ExchangeHistoryResponse> {
        return remote.getHistory(
            params.filtrationType.start, params.filtrationType.end
            , params.base, params.to
        )
    }
}