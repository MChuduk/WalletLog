package com.example.walletlog.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.example.walletlog.R
import com.example.walletlog.model.User
import com.example.walletlog.services.SignInService

class SignIn : AppCompatActivity() {

    private lateinit var loginEditText : EditText;
    private lateinit var passwordEditText: EditText;
    private lateinit var rememberCheckBox : CheckBox;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide();

        findViews();

        if(intent.hasExtra("RememberedUser")) {
            val rememberedUser = intent.extras?.getSerializable("RememberedUser") as User;
            signInUser(rememberedUser.login, rememberedUser.password);
        }
    }

    fun onSignInButtonClick(view : View) {
        val login = loginEditText.text.toString();
        val password = passwordEditText.text.toString();
        val remember = rememberCheckBox.isChecked;

        val user = signInUser(login, password);

        if(user !== null) {
            if(remember)
                SignInService.rememberUser(this, user);
        }
    }

    fun signInUser(login : String, password : String) : User? {
        val user = SignInService.authorizeUser(this, login, password);

        if(user !== null) {
            val intent = Intent(this, MainActivity::class.java);
            val bundle = Bundle();
            bundle.putSerializable("User", user);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        return user;
    }

    fun onLinkedLabelClick(view : View){
        val intent = Intent(this, SignUp::class.java);
        startActivity(intent);
        finish();
    }

    private fun findViews() {
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
    }
}