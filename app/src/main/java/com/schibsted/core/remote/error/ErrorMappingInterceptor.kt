package com.schibsted.core.remote.error

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class ErrorMappingInterceptor(
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response: Response

        response = chain.proceed(request)

        if (response.isSuccessful.not()) {
            val body = response.body()!!
            val source = body.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val charset = body.contentType()!!.charset(Charset.forName("UTF-8"))!!
            val responseBody = buffer.clone().readString(charset)
            val networkException = gson.fromJson(responseBody, NetworkException::class.java)
            throw networkException
        }

        return response
    }
}