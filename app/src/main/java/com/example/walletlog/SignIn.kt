package com.example.walletlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class SignIn : AppCompatActivity() {

    private lateinit var loginEditText : EditText;
    private lateinit var passwordEditText: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViews();
    }

    fun onSignInButtonClick(view : View) {
        val login = loginEditText.text.toString();
        val password = passwordEditText.text.toString();


    }

    fun onLinkedLabelClick(view : View){
        val intent = Intent(this, SignUp::class.java);
        startActivity(intent);
        finish();
    }

    private fun findViews() {
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }
}