package com.schibsted.exchangehistory.mapper

import com.github.mikephil.charting.data.Entry
import com.schibsted.core.mapper.Mapper
import com.schibsted.core.remote.dto.Result
import com.schibsted.core.toFloatDate
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse

class ExchangeViewMapper : Mapper<Result<ExchangeHistoryResponse>, Result<List<Entry>>> {
    override fun map(from: Result<ExchangeHistoryResponse>): Result<List<Entry>> {
        return when (from) {
            is Result.Success -> {
                Result.Success(from.data.rates.toList().map {
                    Entry(it.first.toFloatDate()!!, it.second.EUR)
                }.sortedBy { it.x })
            }
            is Result.Error -> {
                Result.Error(from.exception)
            }
        }
    }
}