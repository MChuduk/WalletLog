package com.example.walletlog.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.walletlog.*
import com.example.walletlog.contracts.*

class SpendingService {
    companion object {

        fun addSpending(context: Context, spending: Spending) : Spending? {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val values = ContentValues();

                values.put(SpendingUser, spending.user);
                values.put(SpendingDate, spending.date);
                values.put(SpendingValue, spending.value);
                values.put(SpendingNote, spending.note);

                val id = db.insertOrThrow(TableSpending, null, values);
                return Spending(id.toString(), spending.user, spending.date, spending.value, spending.note);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return null;
            }
        }

        fun getUserSpending(context: Context, id : String, date : String) : MutableList<Spending> {
            var cursor : Cursor? = null;
            try {
                val db = SqliteDbHelper(context).readableDatabase;
                val selection = "$SpendingUser = ? AND $SpendingDate = ?";
                val selectionArs = arrayOf(id, date);
                val spendingList = mutableListOf<Spending>();

                cursor = db.query(TableSpending, null, selection, selectionArs, null, null, null);

                if(cursor.moveToFirst()){
                    do {
                        val idQuery = cursor.getValueString(context, SpendingId);
                        val user = cursor.getValueString(context, SpendingUser);
                        val dateQuery = cursor.getValueString(context, SpendingDate);
                        val value = cursor.getValueInteger(context, SpendingValue);
                        val note = cursor.getValueString(context, SpendingNote);

                        val spending = Spending(idQuery, user, dateQuery, value, note);
                        spendingList.add(spending);
                    } while (cursor.moveToNext());
                }
                return spendingList;
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
                return arrayListOf();
            }
        }



        fun deleteSpending(context: Context, id : String) {
            try {
                val db = SqliteDbHelper(context).writableDatabase;
                val selection = "$SpendingId = ?";
                val selectionArs = arrayOf(id);

                db.delete(TableSpending, selection, selectionArs);
            } catch (exception : Exception) {
                showToastMessage(context, exception.message.toString());
            }
        }
    }
}