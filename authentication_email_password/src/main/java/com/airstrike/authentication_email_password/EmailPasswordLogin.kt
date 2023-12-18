package com.airstrike.authentication_email_password

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.LoginHandler
import com.airstrike.core.authentification.LoginListener
import com.airstrike.core.authentification.helpers.InputValidator
import com.airstrike.core.authentification.helpers.PasswordManager
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.registration_email_password.R
import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.models.responses.LoggedInUser
import com.airstrike.web_services.network.request_handler.LoginRequestHandler

class EmailPasswordLogin : LoginHandler {

    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnShowHidePassword : ImageButton
    private lateinit var btnLogin : Button
    private lateinit var view : View
    private lateinit var loginListener: LoginListener

    override fun showUIandHandleLogin(
        fragment: Fragment,
        container: LinearLayout,
        loginListener: LoginListener
    ) {
        view = LayoutInflater
            .from(fragment.requireContext())
            .inflate(R.layout.email_password_login, null)

        this.loginListener = loginListener
        initializeUIEElements()

        btnShowHidePassword.setOnClickListener{
            PasswordManager.changePasswordDisplayMode(etPassword,btnShowHidePassword)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (checkIfRequiredDataIsEntered(email,password))
                handleLogin()
            else
                loginListener.onFailedLogin(view.context.getString(R.string.missing_data))

        }
        container.addView(view)
    }
    private fun checkIfRequiredDataIsEntered(email: String, password: String): Boolean {
        return InputValidator.isNotEmpty(email) && InputValidator.isNotEmpty(password)
    }
    private fun initializeUIEElements(){
        etEmail = view.findViewById(R.id.login_email_et)
        etPassword = view.findViewById(R.id.login_password_et)
        btnShowHidePassword = view.findViewById(R.id.login_hide_show_password_btn)
        btnLogin = view.findViewById(R.id.login_btn)
    }
    private fun handleLogin()
    {
        val reqBody = LoginBody(
            etEmail.text.toString(),
            etPassword.text.toString(),
        )
        var loginRequestListener = LoginRequestHandler(reqBody)
        loginRequestListener.sendRequest(object : ResponseListener<LoggedInUser> {
            override fun onSuccess(response: LoggedInUser) {
                Log.i("Logged in user",response.toString())
                val user = com.airstrike.core.authentification.LoggedInUser(
                        response.status!!,
                        response.success.toString(),
                        response.accessToken!!,
                        response.refreshToken!!,
                        response.customer?.email.toString(),
                        response.customer?.firstName.toString(),
                        response.customer?.lastName.toString(),
                        response.customer?.password.toString(),
                        response.customer?.custom?.fields?.phoneNumber.toString()
                        )
                loginListener.onSuccessfulLogin(user)
                }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    loginListener.onFailedLogin(error)
                }
            }

            override fun onFailure(t: Throwable) {
                loginListener.onFailedLogin(t.message.toString())
            }
        })
    }
}