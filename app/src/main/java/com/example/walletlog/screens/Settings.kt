package com.example.walletlog.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.walletlog.R
import com.example.walletlog.model.User
import com.example.walletlog.services.CurrencyService

class Settings() : AppCompatActivity() {

    private lateinit var authorizedUser : User;

    private lateinit var currencySpinner : Spinner;
    private lateinit var currencyService : CurrencyService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        actionBar?.title = "Настройки";

        findViews();
        initComponents();
        setupCurrencySpinner();
        setAuthorizedUser();
    }

    fun onApplyButtonClick(view : View) {
        val intent = Intent(this, MainActivity::class.java);
        val bundle = Bundle();
        bundle.putSerializable("User", authorizedUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private fun setupCurrencySpinner() {
        currencyService.getCurrency();
        currencyService.currencyList.observe(this, {
            val currencyNames = CurrencyService.currencyDictionary.keys.toTypedArray();
            currencySpinner.adapter = ArrayAdapter(this, R.layout.spinner_item, currencyNames);
        })
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                authorizedUser.currency = parent.getItemAtPosition(position).toString();
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setAuthorizedUser() {
        if(intent.hasExtra("User"))
            authorizedUser = intent.extras?.getSerializable("User") as User;
    }

    private fun initComponents() {
        currencyService = ViewModelProvider(this).get(CurrencyService::class.java);
    }

    private fun findViews() {
        currencySpinner = findViewById(R.id.currencySpinner);
    }
}