package com.schibsted.exchangehistory.mapper

import com.schibsted.core.mapper.Mapper
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse
import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry

class ExchangeViewMapper : Mapper<Result<ExchangeHistoryResponse>, Result<List<ExchangeDataEntry>>> {
    override fun map(from: Result<ExchangeHistoryResponse>): Result<List<ExchangeDataEntry>> {
        return when (from) {
            is Result.Success -> {
                Result.Success(from.data.rates.toList().map {
                    ExchangeDataEntry(it.first, it.second.EUR)
                })
            }
            is Result.Error -> {
                Result.Error(from.exception)
            }
        }
    }
}