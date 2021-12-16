package com.example.walletlog

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.walletlog.contracts.*

class SqliteDbHelper(val context: Context) :
    SQLiteOpenHelper(context, DbName, null, DbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        createUsersTable(db);
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TableUsers");
    }

    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    private fun createUsersTable(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE $TableUsers (" +
                "$UserId INTEGER PRIMARY KEY, " +
                "$UserLogin TEXT, " +
                "$UserPassword TEXT, " +
                "$UserBudget INTEGER);");
    }
}