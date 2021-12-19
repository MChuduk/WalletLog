package com.example.walletlog.data

import retrofit2.Response
import retrofit2.Retrofit

class CurrencyRepository {

    suspend fun getCurrency() : Response<Currency> {
        return RetrofitInstance.api.getCurrency();
    }
}