package com.schibsted.exchangehistory.presentation

import com.github.mikephil.charting.formatter.ValueFormatter
import com.schibsted.core.toStringDate

class DateValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toStringDate()
    }
}