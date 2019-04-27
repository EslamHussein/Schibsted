package com.schibsted.exchangehistory.presentation

import com.schibsted.core.toFormattedDate
import java.util.*

sealed class FiltrationType {
    abstract val numberOfDays: Int
    val start: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -numberOfDays)
            return calendar.time.toFormattedDate()
        }

    val end: String
        get() {
            return Date().toFormattedDate()
        }

    object OneMonth : FiltrationType() {
        override val numberOfDays: Int
            get() = 30
    }

    object TwoMonths : FiltrationType() {
        override val numberOfDays: Int
            get() = 60
    }

    object SixMonths : FiltrationType() {
        override val numberOfDays: Int
            get() = 180
    }

    object OneYear : FiltrationType() {
        override val numberOfDays: Int
            get() = 365
    }

    object TwoYears : FiltrationType() {
        override val numberOfDays: Int
            get() = 365 * 2
    }
}