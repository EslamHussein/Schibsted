package com.schibsted.core.remote.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.schibsted.BuildConfig
import com.schibsted.core.remote.error.ErrorMappingInterceptor
import com.schibsted.core.remote.service.CloudConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    single { ErrorMappingInterceptor(get()) }
    single {
        val builder = OkHttpClient.Builder()
        builder/*.addInterceptor(get<ErrorMappingInterceptor>())*/
            .connectTimeout(CloudConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CloudConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CloudConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }
    single { GsonBuilder().create() }
    single {
        Retrofit.Builder()
            .baseUrl(CloudConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
}