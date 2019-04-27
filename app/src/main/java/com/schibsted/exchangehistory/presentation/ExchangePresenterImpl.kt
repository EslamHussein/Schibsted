package com.schibsted.exchangehistory.presentation

import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.remote.dto.Result
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ExchangePresenterImpl(
    view: ExchangeHistoryContract.View,
    errorHandler: ErrorHandler,
    private val useCase: GetExchangeHistoryUseCase
) : ExchangeHistoryContract.Presenter(view, errorHandler),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun getExchangeHistory(filtrationType: FiltrationType, base: Currencies, to: Currencies) {
        launch(coroutineContext) {
            getView()?.showLoading()

            when (val result = withContext(Dispatchers.IO) {
                useCase.execute(GetExchangeHistoryUseCase.Params.create(filtrationType, base, to))
            }) {
                is Result.Success -> {
                    getView()?.showExchange(result.data)
                }
                is Result.Error -> {
                    getErrorHandler()?.proceed(result.exception)
                }
            }
            getView()?.hideLoading()
        }

    }


}
