package com.example.walletlog.services

import android.content.Context
import com.example.walletlog.User
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class SignInService {
    companion object {

        private const val rememberUserFileName = "AppUser.json";

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
    }
}