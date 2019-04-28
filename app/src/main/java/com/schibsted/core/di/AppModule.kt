package com.schibsted.core.di

import com.schibsted.core.exception.DefaultErrorHandler
import com.schibsted.core.exception.ErrorHandler
import com.schibsted.core.executor.CoroutineExecutor
import com.schibsted.core.executor.ExecutionThread
import com.schibsted.core.resource.AppResources
import com.schibsted.core.resource.repository.ResourcesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { AppResources(application = androidApplication()) }
    single { ResourcesRepository(get()) }
    factory<ErrorHandler> {
        DefaultErrorHandler(get())
    }
    factory<ExecutionThread> { CoroutineExecutor() }
}