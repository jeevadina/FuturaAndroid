package com.futuraeducation.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitCall {

    lateinit var retrofit: Retrofit
    private val BASE_URL = URLHelper.productionUrl

    fun retroFitCall() {

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
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