package com.severin.movies.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "1af3cebe6ba136777ed3c08d01ec7484"

    private val keyInterceptorClient = ApiKeyInterceptor(API_KEY)

    private val loggingInterceptor = HttpLoggingInterceptor().also {
        it.apply { it.level = HttpLoggingInterceptor.Level.BODY }
    }
    private val interceptorClient = OkHttpClient.Builder()
        .addInterceptor(keyInterceptorClient)
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofitService: RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(interceptorClient)
            .build()
            .create(RetrofitService::class.java)
    }
}