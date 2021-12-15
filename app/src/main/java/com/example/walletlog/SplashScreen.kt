package com.example.walletlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.walletlog.services.SignInService
import com.example.walletlog.services.SignUpService
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            val signedUser = SignInService.getSignedUser(applicationContext);

            if(signedUser == null) {
                val intent = Intent(applicationContext, SignIn::class.java)
                startActivity(intent);
                finish();
            }
        }
    }
}