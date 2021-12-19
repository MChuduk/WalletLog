package com.example.walletlog.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.example.walletlog.data.Currency
import com.example.walletlog.data.CurrencyRepository
import kotlinx.coroutines.launch;
import retrofit2.Response

class CurrencyService : ViewModel() {
    val repo = CurrencyRepository();
    var currencyList : MutableLiveData<Response<Currency>> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            currencyList.value = repo.getCurrency();
        }
    }
}