package com.schibsted.exchangehistory.presentation

import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry

sealed class ExchangeHistoryState {
    data class Error(val errorMsg: String) : ExchangeHistoryState()
    object Loading : ExchangeHistoryState()
    data class Data(val data: List<ExchangeDataEntry>) : ExchangeHistoryState()
}
