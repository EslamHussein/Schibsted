package com.schibsted.core.resource.repository

import com.schibsted.R
import com.schibsted.core.resource.AppResources

class ResourcesRepository(private val appResources: AppResources) {

    fun getNetworkExceptionMessage(): String = appResources.getString(R.string.no_internet_connection)

    fun getSocketTimeoutExceptionMessage(): String = appResources.getString(R.string.timeout_error_message)

}