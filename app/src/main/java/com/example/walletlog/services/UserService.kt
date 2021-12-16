package com.example.walletlog.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.walletlog.*
import com.example.walletlog.contracts.*
import java.io.File

class UserService {
    companion object {

        fun registerUse(context : Context, login : String, password : String) : User? {
            try {
                val db = SqliteDbHelper(context).writableDatabase;

                var candidate = getUser(context, login);

                if(candidate != null)
                    throw Exception("The user with login $login already exists");

                val values = ContentValues();
                values.put(UserLogin, login);
                values.put(UserPassword, password);

                val result = db.insertOrThrow(TableUsers, null, values);
                candidate = getUser(context, login);
                showToastMessage(context, "Inserted RID: $result");
                return candidate;
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            }
        }

        fun authorizeUser(context: Context, login : String, password: String) : User? {
            try {
                val candidate = getUser(context, login);

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

        fun getUser(context : Context, login : String) : User? {
            var cursor : Cursor? = null;
            try{
                val db = SqliteDbHelper(context).readableDatabase;

                val selection = "$UserLogin = ?";
                val selectionArs = arrayOf(login);

                cursor = db.query(TableUsers, null, selection, selectionArs, null, null, null);

                if(cursor.moveToFirst()){
                    val id = cursor.getValueInteger(context, UserId);
                    val login = cursor.getValueString(context, UserLogin);
                    val password = cursor.getValueString(context, UserPassword);
                    val budget = cursor.getValueInteger(context, UserBudget);

                    return User(id, login, password, budget);
                }
                return null;
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            } finally {
                cursor?.close();
            }
        }

        private fun matchPasswords(candidate : User, password: String) : Boolean {
            return candidate.password == password;
        }
    }
}