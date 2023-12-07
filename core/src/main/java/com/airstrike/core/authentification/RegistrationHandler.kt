package com.airstrike.core.authentification


import android.view.View
import android.widget.LinearLayout

interface RegistrationHandler {
    fun showUIandHandleRegistration(view : View, container : LinearLayout,registrationListener : RegistrationListener)
}