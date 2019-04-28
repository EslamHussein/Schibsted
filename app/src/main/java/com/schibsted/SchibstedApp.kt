package com.schibsted

import android.app.Application
import com.schibsted.core.di.appModule
import com.schibsted.core.remote.di.remoteModule
import com.schibsted.exchangehistory.di.exchangeHistoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SchibstedApp : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@SchibstedApp)
            modules(listOf(remoteModule, appModule, exchangeHistoryModule))
        }

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