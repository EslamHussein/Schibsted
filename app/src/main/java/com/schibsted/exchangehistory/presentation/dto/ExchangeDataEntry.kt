package com.schibsted.exchangehistory.presentation.dto

import com.anychart.chart.common.dataentry.ValueDataEntry

class ExchangeDataEntry internal constructor(date: String, exchange: Double) :
    ValueDataEntry(date, exchange)