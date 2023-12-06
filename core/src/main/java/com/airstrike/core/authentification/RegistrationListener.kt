package com.airstrike.core.authentification

interface RegistrationListener {
    fun onSuccessfulRegistration(registeredUserData : RegisteredUser, )
    fun onFailedRegistration(reason : String)
}