package com.airstrike.stylo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.AuthenticationActivity
import com.airstrike.stylo.R
import com.airstrike.stylo.helpers.InputValidator
import com.airstrike.stylo.helpers.PasswordManager
import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.LoggedInUser
import com.airstrike.web_services.models.responses.RegisteredUser
import com.airstrike.web_services.request_handler.LoginRequestHandler
import com.airstrike.web_services.request_handler.RegistrationRequestHandler

class LoginFragment : Fragment() {


    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnShowHidePassword : ImageButton
    private lateinit var btnRegisterRedirect : TextView
    private lateinit var btnLogin : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        etEmail = view.findViewById(R.id.login_email_et)
        etPassword = view.findViewById(R.id.login_password_et)
        btnShowHidePassword = view.findViewById(R.id.login_hide_show_password_btn)
        btnRegisterRedirect = view.findViewById(R.id.sign_up_redirect_btn)
        btnLogin = view.findViewById(R.id.login_btn)

        btnRegisterRedirect.setOnClickListener {
            (requireActivity() as AuthenticationActivity).loadFragment(RegistrationFragment())
        }

        btnShowHidePassword.setOnClickListener{
            PasswordManager.changePasswordDisplayMode(etPassword,btnShowHidePassword)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (checkIfRequiredDataIsEntered(email,password))
            {
                handleLogin()
            }
            else
            {
                Toast.makeText(context, "Missing data, fill all fields than try again!!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkIfRequiredDataIsEntered(email: String, password: String): Boolean {
        return InputValidator.isNotEmpty(email) && InputValidator.isNotEmpty(password)
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
                Toast.makeText(context, R.string.successful_log_in, Toast.LENGTH_LONG).show()

            }
            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { it1 ->
                    Log.i("Login", it1)
                    Toast.makeText(context, response.error,Toast.LENGTH_LONG).show()}
            }
            override fun onFailure(t: Throwable) {
                Log.e("LoginERROR", t.message.toString())

            }
        })
    }

}