package com.schibsted.core.exception

interface ErrorHandler {
    fun getErrorMessage(error: Throwable): String
}