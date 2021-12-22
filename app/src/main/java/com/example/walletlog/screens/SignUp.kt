package com.example.walletlog.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.walletlog.R
import com.example.walletlog.services.SignUpService

class SignUp : AppCompatActivity() {

    private lateinit var loginEditText : EditText;
    private lateinit var passwordEditText: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide();

        findViews();
    }

    fun onSignUpButtonClick(view : View) {
        val login = loginEditText.text.toString();
        val password = passwordEditText.text.toString();

        SignUpService.registerUse(this, login, password);

 /*       CoroutineScope(Dispatchers.IO).launch {
            UserService.registerUse(applicationContext, login, password);
        }*/
    }

    fun onLinkedLabelClick(view : View){
        val intent = Intent(this, SignIn::class.java);
        startActivity(intent);
        finish();
    }

    private fun findViews() {
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }
}