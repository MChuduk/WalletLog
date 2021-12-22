package com.example.walletlog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://belarusbank.by/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    val api : CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java);
    }
}