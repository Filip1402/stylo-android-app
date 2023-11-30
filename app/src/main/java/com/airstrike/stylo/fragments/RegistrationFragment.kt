package com.airstrike.stylo.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.R
import com.airstrike.stylo.helpers.InputValidator
import com.airstrike.stylo.models.Customer
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.models.responses.RegisteredUser
import com.airstrike.web_services.request_handler.RegistrationRequestHandler


class RegistrationFragment : Fragment() {

    private lateinit var etFirstName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPhoneNum : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnShowHidePassword : ImageButton
    private lateinit var btnLoginRedirect : TextView
    private lateinit var btnRegister : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        etFirstName = view.findViewById(R.id.first_name_et)
        etLastName = view.findViewById(R.id.last_name_et)
        etEmail = view.findViewById(R.id.email_et)
        etPhoneNum = view.findViewById(R.id.phone_num_et)
        etPassword = view.findViewById(R.id.password_et)
        btnShowHidePassword = view.findViewById(R.id.hide_show_password_btn)
        btnLoginRedirect = view.findViewById(R.id.sign_in_redirect_btn)
        btnRegister = view.findViewById(R.id.register_btn)

        btnShowHidePassword.setOnClickListener{
            changePasswordDisplayMode()
        }
        btnLoginRedirect.setOnClickListener{
            TODO("Implement login fragment redirect")
        }
        btnRegister.setOnClickListener{
            if(checkIfDataIsValid()) {
                var customer = getCustomerFromInput()
                val reqBody = RegistrationBody(
                    customer.FirstName,
                    customer.LastName,
                    customer.Email,
                    customer.PhoneNum,
                    customer.Password
                )
                var registrationRequestListener = RegistrationRequestHandler(reqBody)
                registrationRequestListener.sendRequest(object : ResponseListener<RegisteredUser>{
                    override fun onSuccess(response: RegisteredUser) {
                        Log.i("Registered user",response.customer.toString())
                        Toast.makeText(context, R.string.succesful_sign_up, Toast.LENGTH_LONG).show()

                    }

                    override fun onErrorResponse(response: ErrorResponseBody) {
                        Toast.makeText(context, response.Error,Toast.LENGTH_LONG).show()
                        response.Error?.let { it1 -> Log.i("Registration", it1) }
                    }

                    override fun onFailure(t: Throwable) {
                        Log.e("RegistrationERROR", t.message.toString())

                    }
                })

            }
        }
    }
    private fun getCustomerFromInput(): Customer {
        return Customer(
            etFirstName.text.toString(),
            etLastName.text.toString(),
            etEmail.text.toString(),
            etPhoneNum.text.toString(),
            etPassword.text.toString())
    }
    private fun changePasswordDisplayMode()
    {
        if(etPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            btnShowHidePassword.setImageResource(R.drawable.hide_password)
        }
        else {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            btnShowHidePassword.setImageResource(R.drawable.show_password)
        }
    }
    private fun checkIfDataIsValid(): Boolean
    {
        val isEmailValid = InputValidator.validateEmail(etEmail.text.toString())
        val isPhoneValid = InputValidator.validatePhoneNum(etPhoneNum.text.toString())
        val isFirstNameValid = InputValidator.isNotEmpty(etFirstName.text.toString())
        val isLastNameValid = InputValidator.isNotEmpty(etLastName.text.toString())
        val isPasswordValid = InputValidator.isNotEmpty(etPassword.text.toString())
        return if(isFirstNameValid && isLastNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            true
        }else {
            Toast.makeText(context, "User input is invalid!!!", Toast.LENGTH_LONG).show()//just for testing remove after
            false
        }
    }

}

