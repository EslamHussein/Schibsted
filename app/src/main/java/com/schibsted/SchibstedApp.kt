package com.schibsted

import android.app.Application
import com.schibsted.core.remote.di.remoteModule
import com.schibsted.core.di.appModule
import com.schibsted.exchangehistory.di.exchangeHistoryModule
import org.koin.android.ext.android.startKoin

class SchibstedApp : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(remoteModule, appModule, exchangeHistoryModule))

    }

    companion object {

        private var instance: SchibstedApp? = null

        fun get(): SchibstedApp {
            if (instance == null)
                throw IllegalStateException("Something went horribly wrong!!, no application attached!")
            return instance as SchibstedApp
        }
    }


}