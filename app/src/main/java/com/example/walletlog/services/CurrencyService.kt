package com.example.walletlog.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walletlog.data.Currency
import com.example.walletlog.data.CurrencyRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CurrencyService: ViewModel() {
    companion object {
        val currencyDictionary : MutableMap<String, Float> = mutableMapOf();
        fun getCurrencyMultiplier(currency: String) : Float = currencyDictionary[currency]!!;
    }

    var currencyList : MutableLiveData<Response<Currency>> = MutableLiveData()

    private val repo = CurrencyRepository();

    fun getCurrency() {
        viewModelScope.launch {
            currencyList.value = repo.getCurrency();
        }
    }
}