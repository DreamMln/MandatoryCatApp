package com.example.mandatorycatapp.models

import android.accounts.AuthenticatorDescription

//change it to an obj
object CatMockObj {
    //1. create a test-case for this mock data
    //2. implement the func, so it passes our test
    fun validateInput(name: String, price: Int): Boolean{
        //we want a name thats NOT empty and a price that is grater than or equals to 0, then its true. The return type is a boolean
        //if (NOT) name is empty OR if price is less than or equals to 0, Then its true, because name is written, and price is 0 or higher...
        return !(name.isEmpty() || price <= 0)
    }
}