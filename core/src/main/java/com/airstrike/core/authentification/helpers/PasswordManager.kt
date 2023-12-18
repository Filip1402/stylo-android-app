package com.airstrike.core.authentification.helpers

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import com.airstrike.core.R

object PasswordManager {
    fun changePasswordDisplayMode(passwordField: EditText, showHidePassword: ImageButton) {
        if (passwordField.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.hide_password)
        } else {
            passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.show_password)
        }
    }
}