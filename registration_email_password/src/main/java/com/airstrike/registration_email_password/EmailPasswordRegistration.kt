package com.airstrike.registration_email_password

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.helpers.InputValidator
import com.airstrike.core.authentification.helpers.PasswordManager
import com.airstrike.core.authentification.RegistrationHandler
import com.airstrike.core.authentification.RegistrationListener
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.RegisteredUser
import com.airstrike.web_services.request_handler.RegistrationRequestHandler


class EmailPasswordRegistration : RegistrationHandler {

    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPhoneNum : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnShowHidePassword : ImageButton
    private lateinit var btnRegister : Button


    override fun showUIandHandleRegistration(fragment: Fragment, container : LinearLayout,registrationListener : RegistrationListener) {

        val view =
            LayoutInflater.from(fragment.requireContext()).inflate(R.layout.email_login_registration, null)

        getUIReferences(view)
        btnShowHidePassword.setOnClickListener {
            PasswordManager.changePasswordDisplayMode(etPassword, btnShowHidePassword)
        }

        btnRegister.setOnClickListener {
            if (checkIfDataIsValid()) {
                val reqBody = RegistrationBody(
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    etEmail.text.toString(),
                    etPhoneNum.text.toString(),
                    etPassword.text.toString()
                )
                var registrationRequestListener = RegistrationRequestHandler(reqBody)
                registrationRequestListener.sendRequest(object : ResponseListener<RegisteredUser> {
                    override fun onSuccess(response: RegisteredUser) {
                        Log.i("Registered user", response.customer.toString())
                        registrationListener.onSuccessfulRegistration( com.airstrike.core.authentification.RegisteredUser(
                            etFirstName.text.toString(),
                            etLastName.text.toString(),
                            etEmail.text.toString(),
                            etPhoneNum.text.toString(),
                            etPassword.text.toString()
                        ))

                    }

                    override fun onErrorResponse(response: ErrorResponseBody) {
                        response.error?.let { error ->
                            registrationListener.onFailedRegistration(error)
                        }
                    }

                    override fun onFailure(t: Throwable) {
                        registrationListener.onFailedRegistration(t.message.toString())
                    }
                })
            }
            else{
                registrationListener.onFailedRegistration(view.context.getString(R.string.invalid_input_data))
            }

        }
        val container = container
        container.addView(view)
    }

    private fun getUIReferences(view: View) {
        etFirstName = view.findViewById(R.id.first_name_et)
        etLastName = view.findViewById(R.id.last_name_et)
        etEmail = view.findViewById(R.id.email_et)
        etPhoneNum = view.findViewById(R.id.phone_num_et)
        etPassword = view.findViewById(R.id.password_et)
        btnShowHidePassword = view.findViewById(R.id.hide_show_password_btn)
        btnRegister = view.findViewById(R.id.register_btn)
    }

    private fun checkIfDataIsValid(): Boolean
        {
            val isEmailValid = InputValidator.validateEmail(etEmail.text.toString())
            val isPhoneValid = InputValidator.validatePhoneNum(etPhoneNum.text.toString())
            val isFirstNameValid = InputValidator.isNotEmpty(etFirstName.text.toString())
            val isLastNameValid = InputValidator.isNotEmpty(etLastName.text.toString())
            val isPasswordValid = InputValidator.isNotEmpty(etPassword.text.toString())

            return isFirstNameValid && isLastNameValid && isEmailValid && isPhoneValid && isPasswordValid
        }

}