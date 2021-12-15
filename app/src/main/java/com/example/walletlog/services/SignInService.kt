package com.example.walletlog.services

import android.content.Context
import com.example.walletlog.User
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class SignInService {
    companion object {

        fun getSignedUser(context : Context) : User? {
            val file = File(context.filesDir, "AppUser.json");

            if(!file.exists())
                return null;

            val reader = FileReader(file);
            val bufferedReader = BufferedReader(reader);
            val jsonString = bufferedReader.readLine();
            bufferedReader.close();
            reader.close();

            val gson = GsonBuilder().create();
            return gson.fromJson(jsonString, User::class.java);
        }
    }
}