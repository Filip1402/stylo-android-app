package com.airstrike.core.authentification

import android.widget.LinearLayout
import androidx.fragment.app.Fragment

interface RegistrationHandler {
    fun showUIandHandleRegistration(fragment : Fragment, container : LinearLayout,registrationListener : RegistrationListener)
}