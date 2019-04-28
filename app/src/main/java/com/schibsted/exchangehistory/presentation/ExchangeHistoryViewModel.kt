package com.schibsted.exchangehistory.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
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

    private lateinit var exchangeData: MutableLiveData<List<Entry>>

    private lateinit var isLoading: MutableLiveData<Boolean>
    private lateinit var showError: MutableLiveData<String>

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = executionThread.mainScheduler + job

    fun getExchangeData(): LiveData<List<Entry>> {
        if (!::exchangeData.isInitialized) {
            exchangeData = MutableLiveData()
        }
        return exchangeData
    }

    fun isShowLoading(): LiveData<Boolean> {
        if (!::isLoading.isInitialized)
            isLoading = MutableLiveData()
        return isLoading
    }

    fun getError(): LiveData<String> {
        if (!::showError.isInitialized)
            showError = MutableLiveData()
        return showError
    }

    fun updateExchangeHistory(filtrationType: FiltrationType, base: Currencies, to: Currencies) {
        launch(coroutineContext) {
            isLoading.value = true
            when (val result = withContext(executionThread.ioScheduler) {
                useCase.execute(GetExchangeHistoryUseCase.Params.create(filtrationType, base, to))
            }) {
                is Result.Success -> {
                    exchangeData.value = result.data
                }
                is Result.Error -> {
                    showError.value = errorHandler.getErrorMessage(result.exception)
                }
            }
            isLoading.value = true
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}