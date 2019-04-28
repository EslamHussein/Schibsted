package com.schibsted.exchangehistory.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.schibsted.TestCoroutinesExecutor
import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.executor.ExecutionThread
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.MockedErrorHandler
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase
import com.schibsted.fakedata.DataFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class ExchangeHistoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var errorHandler: ErrorHandler
    private lateinit var executionThread: ExecutionThread
    @Mock
    private lateinit var useCase: GetExchangeHistoryUseCase

    private lateinit var viewModel: ExchangeHistoryViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        errorHandler = MockedErrorHandler()
        executionThread = TestCoroutinesExecutor()
        viewModel = ExchangeHistoryViewModel(errorHandler, executionThread, useCase)
    }


    @Test
    fun test_no_internet_show_internet_message() {
        val testParam = GetExchangeHistoryUseCase.Params.create(FiltrationType.OneMonth)

        runBlocking {
            Mockito.doReturn(Result.Error(IOException())).`when`(useCase).execute(testParam)
        }
        viewModel.updateExchangeHistory(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)
        Assert.assertEquals("No internet connection", viewModel.showError.value)
    }


    @Test
    fun test_get_data_success() {
        val testParam = GetExchangeHistoryUseCase.Params.create(FiltrationType.OneMonth)
        val expectedList = DataFactory.getListEntry()
        runBlocking {
            Mockito.doReturn(Result.Success(expectedList)).`when`(useCase).execute(testParam)
        }
        viewModel.updateExchangeHistory(FiltrationType.OneMonth, Currencies.USD, Currencies.EUR)
        Assert.assertEquals(expectedList.size, viewModel.exchangeData.value!!.size)
    }

}