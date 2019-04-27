package com.schibsted.exchangehistory.di

import com.schibsted.exchangehistory.data.remote.ExchangeApi
import com.schibsted.exchangehistory.data.remote.ExchangeRemoteDataSource
import com.schibsted.exchangehistory.data.remote.ExchangeRemoteDataSourceImpl
import com.schibsted.exchangehistory.domain.GetExchangeHistoryUseCase
import com.schibsted.exchangehistory.mapper.ExchangeViewMapper
import com.schibsted.exchangehistory.presentation.ExchangeHistoryContract
import com.schibsted.exchangehistory.presentation.ExchangePresenterImpl
import com.schibsted.exchangehistory.repository.ExchangeRepository
import com.schibsted.exchangehistory.repository.ExchangeRepositoryImpl
import org.koin.dsl.module.module
import retrofit2.Retrofit

val exchangeHistoryModule = module {
    factory { get<Retrofit>().create(ExchangeApi::class.java) }

    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    single<ExchangeRemoteDataSource> { ExchangeRemoteDataSourceImpl(get(), get()) }

    factory { GetExchangeHistoryUseCase(get(), get()) }
    factory<ExchangeHistoryContract.Presenter> { (view: ExchangeHistoryContract.View) ->
        ExchangePresenterImpl(view, get(), get())
    }

    factory { ExchangeViewMapper() }
}