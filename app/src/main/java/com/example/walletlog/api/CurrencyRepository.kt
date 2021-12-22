package com.example.walletlog.api

import retrofit2.Response

class CurrencyRepository {

    suspend fun getCurrency() : Response<Currency> {
        return RetrofitInstance.api.getCurrency();
    }
}