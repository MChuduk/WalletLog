package com.example.walletlog

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.walletlog.contracts.*

class SqliteDbHelper(val context: Context) :
    SQLiteOpenHelper(context, DbName, null, DbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        createUsersTable(db);
        createTableSpending(db);
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TableUsers");
        db.execSQL("DROP TABLE IF EXISTS $TableSpending");
    }

    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    private fun createUsersTable(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE $TableUsers (" +
                "$UserId INTEGER PRIMARY KEY, " +
                "$UserLogin TEXT, " +
                "$UserPassword TEXT, " +
                "$UserBudget REAL NOT NULL DEFAULT 0.0, " +
                "$UserCurrency TEXT NOT NULL DEFAULT BYN" +
                ");");
    }

    private fun createTableSpending(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TableSpending (" +
                "$SpendingId INTEGER PRIMARY KEY, " +
                "$SpendingUser INTEGER NOT NULL, " +
                "$SpendingDate TEXT, " +
                "$SpendingValue INTEGER NOT NULL DEFAULT 0, " +
                "$SpendingNote TEXT, " +
                "$SpendingCategory TEXT, " +
                "$SpendingCommit INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY ($SpendingUser) REFERENCES $TableUsers ($UserId) ON DELETE CASCADE" +
                ");");
    }
}