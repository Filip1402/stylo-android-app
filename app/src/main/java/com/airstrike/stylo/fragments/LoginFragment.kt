package com.airstrike.stylo.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.airstrike.authentication_email_password.EmailPasswordLogin
import com.airstrike.authentication_google.GoogleLogin
import com.airstrike.core.authentification.LoggedInUser
import com.airstrike.core.authentification.LoginHandler
import com.airstrike.core.authentification.LoginListener
import com.airstrike.stylo.AuthenticationActivity
import com.airstrike.stylo.R
import com.airstrike.stylo.ShoppingActivity
import com.airstrike.stylo.helpers.SecurePreferencesManager

class LoginFragment : Fragment(), LoginListener {


    private lateinit var btnRegisterRedirect : TextView
    private  var loginHandlers : List<LoginHandler> = listOf(EmailPasswordLogin(),GoogleLogin())
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

        //temporary for testing

        loginHandlers.forEach { handler->
                handler.showUIandHandleLogin(this,view.findViewById(R.id.login_frame),this)
            }
        btnRegisterRedirect = view.findViewById(R.id.sign_up_redirect_btn)

        btnRegisterRedirect.setOnClickListener {
            (requireActivity() as AuthenticationActivity).loadFragment(RegistrationFragment())
        }
    }
    private fun redirectToHomepage()
    {
        val intent = Intent(this.context, ShoppingActivity::class.java)
        //intent.putExtra("user",user);
        startActivity(intent)
    }
    override fun onSuccessfulLogin(loggedInUser: LoggedInUser) {
            Toast.makeText(this.context, R.string.successful_log_in, Toast.LENGTH_LONG).show()
            val securePreferencesManager = SecurePreferencesManager(this.requireContext())
            loggedInUser.password = "" //dont store the password
            securePreferencesManager.saveObject("loggedInUser",loggedInUser)
            redirectToHomepage()
    }
    override fun onFailedLogin(reason: String) {
        Toast.makeText(context, reason,Toast.LENGTH_LONG).show()
    }


}