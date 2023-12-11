package com.airstrike.core.authentification

import android.widget.LinearLayout
import androidx.fragment.app.Fragment

interface LoginHandler {
    fun showUIandHandleLogin(fragment : Fragment, container : LinearLayout,loginToken : LoginToken, loginListener: LoginListener)
}