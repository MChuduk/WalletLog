package com.example.walletlog

import java.io.Serializable

class User(var id : String, var login : String, var password : String, var budget : Float, var currency : String)
    : Serializable { }