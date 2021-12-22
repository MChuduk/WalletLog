package com.example.walletlog.services

import android.content.Context
import com.example.walletlog.model.User
import com.example.walletlog.showToastMessage
import java.io.File

class SignInService {
    companion object {

        private const val rememberUserFileName = "AppUser.json";

        fun authorizeUser(context: Context, login : String, password: String) : User? {
            try {
                val candidate = UserService.getUser(context, login);

                if(candidate === null)
                    throw Exception("Can't find user with login: $login");

                if(!matchPasswords(candidate, password))
                    throw Exception("Wrong password for user $login");

                return candidate;
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            }
        }

        fun rememberUser(context: Context, user : User) {
            val file = File(context.filesDir, rememberUserFileName);

            if(!file.exists())
                file.createNewFile();

            JsonService.exportToJson(context, file, user);
        }

        fun getRememberedUser(context : Context) : User? {
            val file = File(context.filesDir, rememberUserFileName);

            if(!file.exists())
                return null;

            return JsonService.importFromJson(context, file);
        }

        fun logoutUser(context : Context) {
            val file = File(context.filesDir, rememberUserFileName);

            if(!file.exists())
                return;

            file.delete();
        }

        private fun matchPasswords(candidate : User, password: String) : Boolean {
            return candidate.password == password;
        }
    }
}