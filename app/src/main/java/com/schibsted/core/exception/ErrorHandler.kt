package com.schibsted.core.exception

import com.schibsted.core.view.ShowErrorView

interface ErrorHandler {
    fun proceed(error: Throwable)
    fun attachView(view: ShowErrorView)
    fun detachView()
}