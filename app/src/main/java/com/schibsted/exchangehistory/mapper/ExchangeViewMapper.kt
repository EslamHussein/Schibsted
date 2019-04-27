package com.schibsted.exchangehistory.mapper

import com.schibsted.core.mapper.Mapper
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.CustomDataEntry
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse

class ExchangeViewMapper:Mapper<Result<ExchangeHistoryResponse>,Result<List<CustomDataEntry>>> {
    override fun map(from: Result<ExchangeHistoryResponse>): Result<List<CustomDataEntry>> {
        return  when (from) {
            is Result.Success -> {
                Result.Success(from.data.rates.toList().map {
                    CustomDataEntry(it.first, it.second.EUR)
                })
            }
            is Result.Error -> {
                Result.Error(from.exception)
            }
        }
    }
}