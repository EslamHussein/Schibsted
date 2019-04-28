package com.schibsted.exchangehistory.domain

import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.mapper.ExchangeViewMapper
import com.schibsted.exchangehistory.presentation.FiltrationType
import com.schibsted.exchangehistory.repository.ExchangeRepository
import com.schibsted.fakedata.DataFactory
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetExchangeHistoryUseCaseTest {

    private lateinit var getExchangeHistoryUseCase: GetExchangeHistoryUseCase
    @Mock
    lateinit var exchangeRepository: ExchangeRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getExchangeHistoryUseCase = GetExchangeHistoryUseCase(exchangeRepository, ExchangeViewMapper())
    }

    @Test
    fun getExchangeHistorySuccess() = runBlocking {
        val testParam = GetExchangeHistoryUseCase.Params.create(FiltrationType.OneMonth)

        val result =
            Result.Success(
                DataFactory.getExchangeHistory(
                    testParam.base,
                    testParam.filtrationType.start,
                    testParam.filtrationType.end
                )
            )
        Mockito.doReturn(result).`when`(exchangeRepository).getHistory(testParam)

        val testResult = getExchangeHistoryUseCase.execute(testParam)


        Assert.assertThat(testResult, CoreMatchers.instanceOf(Result.Success::class.java))

    }

    @Test(expected = IllegalArgumentException::class)
    fun getExchangeHistoryFailureWithNullParams() = runBlocking {

        val testParam = GetExchangeHistoryUseCase.Params.create(FiltrationType.OneMonth)

        val result =
            Result.Success(
                DataFactory.getExchangeHistory(
                    testParam.base,
                    testParam.filtrationType.start,
                    testParam.filtrationType.end
                )
            )
        Mockito.doReturn(result).`when`(exchangeRepository).getHistory(testParam)

        Assert.assertNotNull(getExchangeHistoryUseCase.execute(null))


    }
}