package com.schibsted.exchangehistory.presentation

import com.github.mikephil.charting.data.Entry


sealed class ExchangeHistoryState {
    data class Error(val errorMsg: String, val data: List<Entry>? = null) : ExchangeHistoryState()
    object Loading : ExchangeHistoryState()
    data class Data(val data: List<Entry>) : ExchangeHistoryState()
}
