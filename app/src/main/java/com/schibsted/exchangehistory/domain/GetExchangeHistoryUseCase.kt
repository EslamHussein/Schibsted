package com.schibsted.exchangehistory.domain

import com.schibsted.core.domain.UseCase
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.mapper.ExchangeViewMapper
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry
import com.schibsted.exchangehistory.repository.ExchangeRepository

class GetExchangeHistoryUseCase(private val repository: ExchangeRepository, private val mapper: ExchangeViewMapper) :
    UseCase<GetExchangeHistoryUseCase.Params, Result<List<ExchangeDataEntry>>> {
    override suspend fun execute(param: Params?): Result<List<ExchangeDataEntry>> {
        if (param == null) throw IllegalArgumentException("Params cannot be null")
        val result = repository.getHistory(param)
        return mapper.map(result)
    }

    data class Params constructor(
        val filtrationType: FiltrationType,
        val base: String,
        val to: String
    ) {
        companion object {
            fun create(
                filtrationType: FiltrationType, base: Currencies = Currencies.USD,
                to: Currencies = Currencies.EUR
            ): Params {
                return Params(filtrationType, base.name, to.name)
            }
        }
    }
}