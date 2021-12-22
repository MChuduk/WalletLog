package com.example.walletlog.api

import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {

    @GET("api/kursExchange?city=Минск")
    suspend fun getCurrency() : Response<Currency>
}