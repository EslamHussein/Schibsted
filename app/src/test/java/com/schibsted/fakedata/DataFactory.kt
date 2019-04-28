package com.schibsted.fakedata

import com.schibsted.core.toFormattedDate
import com.schibsted.exchangehistory.data.remote.dto.Currency
import com.schibsted.exchangehistory.data.remote.dto.ExchangeHistoryResponse
import java.time.LocalDate
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random


object DataFactory {


    fun getExchangeHistory(base: String, startDate: String, endDate: String): ExchangeHistoryResponse {
        return ExchangeHistoryResponse(base, getRates(startDate, endDate))
    }


    private fun getRates(startDate: String, endDate: String): Map<String, Currency> {
        return mapOf(getRandomDate(startDate, endDate) to getCurrency())
    }

    private fun getCurrency(): Currency {

        return Currency(Random.nextFloat())
    }

    private fun getRandomDate(startDateString: String, endDateString: String): String {

        val startDate = LocalDate.parse(startDateString) //start date
        val start = startDate.toEpochDay()
        println(start)

        val endDate = LocalDate.parse(endDateString) //end date
        val end = endDate.toEpochDay()
        println(start)

        val randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().asLong
        return Date(randomEpochDay).toFormattedDate()
    }


}