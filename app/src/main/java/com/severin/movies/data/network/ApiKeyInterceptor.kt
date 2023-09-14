package com.severin.movies.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val api_key: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter(API_KEY_NAME, api_key).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }

    companion object {
        private const val API_KEY_NAME = "api_key"
    }
}
