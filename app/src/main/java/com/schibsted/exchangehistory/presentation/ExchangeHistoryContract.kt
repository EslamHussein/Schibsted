package com.schibsted.exchangehistory.presentation

import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.presenter.BasePresenter
import com.schibsted.core.view.MvpView
import com.schibsted.core.view.ShowErrorView
import com.schibsted.core.view.ShowLoadingView
import com.schibsted.exchangehistory.domain.Currencies
import com.schibsted.exchangehistory.presentation.dto.ExchangeDataEntry

interface ExchangeHistoryContract {
    interface View : MvpView, ShowErrorView, ShowLoadingView {
        fun showExchange(result: List<ExchangeDataEntry>)
    }

    abstract class Presenter(view: View, errorHandler: ErrorHandler) :
        BasePresenter<View>(view, errorHandler) {
        abstract fun getExchangeHistory(
            filtrationType: FiltrationType, base: Currencies = Currencies.USD, to: Currencies
        )
    }
}