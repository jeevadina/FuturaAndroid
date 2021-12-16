package com.futuraeducation.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetroFitCall {

    lateinit var retrofit: Retrofit
    private val BASE_URL = URLHelper.productionUrl

    fun retroFitCall() {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(TimeoutInterceptorImpl())
            .connectTimeout(90, TimeUnit.SECONDS).build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun retroFitCallPostAssign() {

        retrofit = Retrofit.Builder()
            .baseUrl(URLHelper.assignment)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}