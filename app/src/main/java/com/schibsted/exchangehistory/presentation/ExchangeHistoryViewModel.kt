package com.schibsted.exchangehistory.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.executor.ExecutionThread
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExchangeHistoryViewModel(
    private val errorHandler: ErrorHandler, private val executionThread: ExecutionThread,
    private val useCase: GetExchangeHistoryUseCase
) : ViewModel(), CoroutineScope {

    private lateinit var exchangeDataState: MutableLiveData<ExchangeHistoryState>

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = executionThread.mainScheduler + job



    fun getExchangeData(): LiveData<ExchangeHistoryState> {
        if (!::exchangeDataState.isInitialized) {
            exchangeDataState = MutableLiveData()
        }
        return exchangeDataState
    }


    fun updateExchangeHistory(filtrationType: FiltrationType, base: Currencies, to: Currencies) {
        launch(coroutineContext) {
            exchangeDataState.value = ExchangeHistoryState.Loading
            when (val result = withContext(executionThread.ioScheduler) {
                useCase.execute(GetExchangeHistoryUseCase.Params.create(filtrationType, base, to))
            }) {
                is Result.Success -> {
                    exchangeDataState.value = ExchangeHistoryState.Data(result.data)
                }
                is Result.Error -> {
                    exchangeDataState.value = ExchangeHistoryState.Error(errorHandler.getErrorMessage(result.exception))
                }
            }
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}