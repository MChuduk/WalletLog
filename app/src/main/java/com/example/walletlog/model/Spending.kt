package com.example.walletlog.model

class Spending(val id : String,
               val user : String,
               val date : String,
               val value : Int,
               val note : String,
               val category : String,
               val commit : Int) {
}