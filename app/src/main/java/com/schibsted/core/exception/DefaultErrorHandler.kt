package com.schibsted.core.exception

import com.schibsted.R
import com.schibsted.core.remote.error.NetworkException
import com.schibsted.core.resource.AppResources
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class DefaultErrorHandler constructor(private val resourceManager: AppResources) : ErrorHandler {
    override fun getErrorMessage(error: Throwable): String {

        return when (error) {
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
    }


}