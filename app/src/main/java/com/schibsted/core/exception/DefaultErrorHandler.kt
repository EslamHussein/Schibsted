package com.schibsted.core.exception

import com.schibsted.R
import com.schibsted.core.remote.error.NetworkException
import com.schibsted.core.resource.AppResources
import com.schibsted.core.view.ShowErrorView
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class DefaultErrorHandler constructor(private val resourceManager: AppResources) : ErrorHandler {

    private lateinit var view: WeakReference<ShowErrorView>

    override fun proceed(error: Throwable) {
        val view = view.get()
            ?: throw IllegalStateException("View must be attached to ErrorHandler")
        val message = when (error) {
            is IOException, is UnknownHostException, is SocketException
            -> resourceManager.getString(R.string.timeout_error_message)
            is SocketTimeoutException -> {
                resourceManager.getString(R.string.timeout_error_message)
            }
            is NetworkException -> {
                error.error ?: resourceManager.getString(R.string.unknown_error)
            }
            else -> resourceManager.getString(R.string.unknown_error)
        }
        view.showError(message)

    }

    override fun attachView(view: ShowErrorView) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        view.clear()
    }
}