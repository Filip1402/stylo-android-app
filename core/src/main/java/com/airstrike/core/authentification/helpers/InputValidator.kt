package com.airstrike.core.authentification.helpers

import android.util.Patterns

object InputValidator {

    fun validateEmail(email : String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePhoneNum(phoneNum: String): Boolean {
        return Patterns.PHONE.matcher(phoneNum).matches() && phoneNum.length == 10 //keep in mind this isn't at all bullet proof
        TODO("Implemt better check that supports international numbers and standards")
    }
    fun isNotEmpty(input: String): Boolean {
        return input.isNotEmpty()
    }

}