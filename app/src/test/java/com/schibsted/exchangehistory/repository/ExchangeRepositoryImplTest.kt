package com.schibsted.exchangehistory.repository

import com.schibsted.core.remote.dto.Result
import com.schibsted.core.remote.error.NetworkException
import com.schibsted.exchangehistory.data.remote.ExchangeRemoteDataSource
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.fakedata.DataFactory
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ExchangeRepositoryImplTest {
    private lateinit var exchangeRepository: ExchangeRepository

    @Mock
    private lateinit var remoteDataSource: ExchangeRemoteDataSource

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        exchangeRepository = ExchangeRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test get data from date source as Result Success`() = runBlocking {

        val expected = DataFactory.getExchangeHistory(
            Currencies.USD.name,
            FiltrationType.OneMonth.start,
            FiltrationType.OneMonth.end
        )
        Mockito.doReturn(Result.Success(expected))
            .`when`(remoteDataSource).getHistory(
                FiltrationType.OneMonth.start, FiltrationType.OneMonth.end,
                Currencies.USD.name, Currencies.EUR.name
            )

        val params = GetExchangeHistoryUseCase.Params.create(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)
        val result = exchangeRepository.getHistory(params)

        Assert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
        Assert.assertEquals((result as Result.Success).data.base, Currencies.USD.name)

    }

    @Test
    fun `test get data from date source as Result Error`() = runBlocking {


        Mockito.doReturn(Result.Error(NetworkException("start_at parameter format")))
            .`when`(remoteDataSource).getHistory(
                "123-12-02", FiltrationType.OneMonth.end, // pass wrong date
                Currencies.USD.name, Currencies.EUR.name
            )
        val params = GetExchangeHistoryUseCase.Params.create(
            FiltrationType.OneMonth,
            Currencies.USD, Currencies.EUR
        )
        val result = exchangeRepository.getHistory(params)
        Assert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        Assert.assertThat((result as Result.Error), CoreMatchers.instanceOf(NetworkException::class.java))
        Assert.assertEquals((result.exception as NetworkException).error, "start_at parameter format")

    }


}