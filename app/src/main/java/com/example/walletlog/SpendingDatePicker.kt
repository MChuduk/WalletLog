package com.example.walletlog

import android.util.Log
import android.widget.CalendarView

class SpendingDatePicker() {

    constructor(calendarView : CalendarView) : this() {
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            run {
                Log.d("date: ", "$year + $month + $dayOfMonth");
            }
        };
    }
}