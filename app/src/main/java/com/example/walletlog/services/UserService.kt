package com.example.walletlog.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.walletlog.*
import com.example.walletlog.contracts.*
import com.example.walletlog.model.User

class UserService {
    companion object {

        fun getUser(context : Context, login : String) : User? {
            var cursor : Cursor? = null;
            try{
                val db = SqliteDbHelper(context).readableDatabase;

                val selection = "$UserLogin = ?";
                val selectionArs = arrayOf(login);

                cursor = db.query(TableUsers, null, selection, selectionArs, null, null, null);

                if(cursor.moveToFirst()){
                    val id = cursor.getValueString(context, UserId);
                    val login = cursor.getValueString(context, UserLogin);
                    val password = cursor.getValueString(context, UserPassword);
                    val budget = cursor.getValueFloat(context, UserBudget);
                    val currency = cursor.getValueString(context, UserCurrency);

                    return User(id, login, password, budget, currency);
                }
                return null;
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            } finally {
                cursor?.close();
            }
        }

        fun insertUser(context: Context, user: User) : User? {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val values = ContentValues();

                values.put(UserLogin, user.login);
                values.put(UserPassword, user.password);
                values.put(UserBudget, user.budget);
                values.put(UserCurrency, user.currency);
                val id = db.insertOrThrow(TableUsers, null, values);
                return User(id.toString(), user.login, user.password, user.budget, user.currency);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            }
        }

        fun deleteUser(context: Context, id : String) {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val selection = "$UserId = ?";
                val selectionArs = arrayOf(id);

                db.delete(TableUsers, selection, selectionArs);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
            }
        }

        fun changeBudget(context: Context, id : String,  amount : Float) {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val selection = "$UserId = ?";
                val selectionArs = arrayOf(id);

                val values = ContentValues();
                values.put(UserBudget, amount);
                db.update(TableUsers, values, selection, selectionArs);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
            }
        }

        fun changeCurrency(context: Context, id: String, currency : String) {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val selection = "$UserId = ?";
                val selectionArs = arrayOf(id);

                val values = ContentValues();
                values.put(UserCurrency, currency);
                db.update(TableUsers, values, selection, selectionArs);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
            }
        }
    }
}