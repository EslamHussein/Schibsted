package com.schibsted.exchangehistory.mapper

import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.fakedata.DataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeViewMapperTest {

    @Test
    fun `test mapping data by size`() {
        val mapper = ExchangeViewMapper()
        val duration = FiltrationType.OneMonth
        val expectedResult =
            Result.Success(DataFactory.getExchangeHistory(Currencies.USD.name, duration.start, duration.end))

        val actualResult = mapper.map(expectedResult)

        assertEquals(
            (actualResult as Result.Success).data.size,
            expectedResult.data.rates.size
        )
    }

}