package com.example.walletlog.services

import android.content.Context
import com.example.walletlog.User
import com.example.walletlog.showToastMessage

class SignUpService {

    companion object {

        fun registerUse(context : Context, login : String, password : String) : User? {
            try {
                val candidate = UserService.getUser(context, login);

                if(candidate != null)
                    throw Exception("The user with login $login already exists");

                val user = User("", login, password, 0.0F);
                return UserService.insertUser(context, user);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            }
        }
    }
}