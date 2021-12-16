package com.example.walletlog

import android.content.Context
import android.database.Cursor
import android.text.Editable
import android.widget.EditText
import android.widget.Toast

fun showToastMessage(context : Context, message : String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
    toast.show();
}

fun Cursor.getValueInteger(context: Context, columnName : String) : Int {
    val columnIndex = this.getColumnIndex(columnName);
    return this.getInt(columnIndex);
}

fun Cursor.getValueString(context: Context, columnName : String) : String {
    val columnIndex = this.getColumnIndex(columnName);
    return this.getString(columnIndex);
}