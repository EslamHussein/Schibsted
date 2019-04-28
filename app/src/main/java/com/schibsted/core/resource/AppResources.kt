package com.schibsted.core.resource

import android.app.Application
import android.content.Context

class AppResources(private val context: Context) {

    fun getString(resId: Int): String = context.getString(resId)

}