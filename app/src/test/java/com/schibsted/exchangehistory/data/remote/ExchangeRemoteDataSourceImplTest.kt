package com.schibsted.exchangehistory.data.remote

import com.schibsted.core.remote.di.remoteModule
import com.schibsted.core.remote.dto.Result
import com.schibsted.core.remote.error.NetworkException
import com.schibsted.exchangehistory.di.exchangeHistoryModule
import com.schibsted.exchangehistory.domain.Currencies
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import java.io.File


class ExchangeRemoteDataSourceImplTest : AutoCloseKoinTest() {

    private lateinit var mockServer: MockWebServer

    private lateinit var remoteDataSource: ExchangeRemoteDataSource

    @Before
    fun before() {
        mockServer = MockWebServer()
        mockServer.start()

        startKoin {
            modules(listOf(remoteModule, exchangeHistoryModule))
        }
        remoteDataSource = get()

    }

    @Test
    fun `test success response from server`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(200)
            setBody(getJson("json/history/exchangeHistory.json"))
        }

        mockServer.enqueue(mockedResponse)

        val result = remoteDataSource.getHistory(
            "2019-01-01", "2019-04-17",
            Currencies.USD.name, Currencies.EUR.name
        )

        Assert.assertThat(result, CoreMatchers.instanceOf(Result.Success::class.java))
    }


    @Test
    fun `test failure response from server with wrong date`() = runBlocking {

        val mockedResponse = MockResponse().apply {
            setResponseCode(400)
            setBody(getJson("json/history/exchangeHistoryWrongDate.json"))
        }

        mockServer.enqueue(mockedResponse)

        val result = remoteDataSource.getHistory(
            "201-01-01", "2019-04-17",
            Currencies.USD.name, Currencies.EUR.name
        )
        val error = (result as Result.Error).exception as NetworkException


        Assert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
        Assert.assertThat(error, CoreMatchers.instanceOf(NetworkException::class.java))
        Assert.assertEquals(error.error, "start_at parameter format")

    }

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

}