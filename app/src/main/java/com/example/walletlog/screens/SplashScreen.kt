package com.example.walletlog.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.walletlog.services.SignInService

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rememberedUser = SignInService.getRememberedUser(this);
        val intent = Intent(this, SignIn::class.java)

        Log.d("qqqq ", (rememberedUser == null).toString())

        if(rememberedUser != null) {
            val bundle = Bundle();
            bundle.putSerializable("RememberedUser", rememberedUser);
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }
}