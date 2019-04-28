package com.schibsted.exchangehistory.data.remote.dto

data class ExchangeHistoryResponse(val base: String, val rates: Map<String, Currency>)
data class Currency(val EUR: Float)
