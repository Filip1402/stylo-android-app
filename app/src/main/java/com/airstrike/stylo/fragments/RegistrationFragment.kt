package com.airstrike.stylo.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.RegisteredUser
import com.airstrike.core.authentification.RegistrationListener
import com.airstrike.registration_email_password.EmailPasswordRegistration
import com.airstrike.stylo.AuthenticationActivity
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Customer


class RegistrationFragment : Fragment(), RegistrationListener {

    private lateinit var regHanlder : EmailPasswordRegistration
    private lateinit var btnLoginRedirect : TextView
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
        regHanlder = EmailPasswordRegistration()
        regHanlder.showUIandHandleRegistration(view,view.findViewById(R.id.font),this)
        btnLoginRedirect = view.findViewById(R.id.sign_in_redirect_btn)
        btnLoginRedirect.setOnClickListener{
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        (requireActivity() as AuthenticationActivity).loadFragment(LoginFragment())
    }

    override fun onSuccessfulRegistration(registeredUserData: RegisteredUser) {
        Toast.makeText(context, com.airstrike.registration_email_password.R.string.succesful_sign_up, Toast.LENGTH_LONG)
            .show()
        Handler(Looper.getMainLooper()).postDelayed({redirectToLogin()},1000)      //redirect to registration fragment, delay is here to give user time to read the toast
    }

    override fun onFailedRegistration(reason: String) {
        Toast.makeText(context, reason,Toast.LENGTH_LONG).show()
    }


}

