package com.example.walletlog

import android.util.Log
import android.widget.CalendarView

class SpendingDatePicker() {

    private var year : Int = 0;
    private var month : Int = 0;
    private var dayOfMonth : Int = 0;

    constructor(calendarView : CalendarView, activity: MainActivity) : this() {
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            run {
                this.year = year;
                this.month = month;
                this.dayOfMonth = dayOfMonth;

                activity.showUserSpendingAtDate();
            }
        };
    }

    fun getSelectedDate() : String {
        return "$year-$month-$dayOfMonth";
    }
}