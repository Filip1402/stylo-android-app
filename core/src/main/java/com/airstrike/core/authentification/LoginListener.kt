package com.airstrike.core.authentification

interface LoginListener {
    fun onSuccessfulLogin(loggedInUser: LoggedInUser)
    fun onFailedLogin(reason : String)
}