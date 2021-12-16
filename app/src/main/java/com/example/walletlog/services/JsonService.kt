package com.example.walletlog.services

import android.content.Context
import com.example.walletlog.User
import com.example.walletlog.showToastMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.Exception

class JsonService {
    companion object {

        fun exportToJson(context : Context, file : File, obj : User) {
            var writer : FileWriter? = null;
            var bufferedWriter : BufferedWriter? = null;
            try {
                val gson = Gson();
                val jsonString = gson.toJson(obj);
                writer = FileWriter(file, false);
                bufferedWriter = BufferedWriter(writer);
                writer.write(jsonString);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
            } finally {
                bufferedWriter?.close();
                writer?.close();
            }
        }

        fun importFromJson(context : Context, file : File) : User? {
            var reader : FileReader? = null;
            var bufferedReader : BufferedReader? = null;
            try {
                reader = FileReader(file);
                bufferedReader = BufferedReader(reader);

                val jsonString = bufferedReader.readLine();
                bufferedReader.close();
                reader.close();

                val gson = GsonBuilder().create();
                return gson.fromJson(jsonString, User::class.java);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            } finally {
                bufferedReader?.close();
                reader?.close();
            }
        }
    }
}