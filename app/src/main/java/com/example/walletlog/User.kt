package com.example.walletlog

import java.io.Serializable

class User(var id : Int, var login : String, var password : String, var budget : Int) : Serializable {

}