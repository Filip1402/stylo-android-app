package com.airstrike.authentication_google

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.LoginHandler
import com.airstrike.core.authentification.LoginListener
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.registration_google.R
import com.airstrike.web_services.models.LoginBody
import com.airstrike.web_services.network.request_handler.LoginRequestHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class GoogleLogin : LoginHandler{

    private lateinit var loginButton: SignInButton
    private val RC_SIGN_IN = 1001
    private lateinit var loginListener: LoginListener
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient : GoogleSignInClient

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestProfile()
        .requestEmail()
        .build()

    override fun showUIandHandleLogin(
        fragment: Fragment,
        container: LinearLayout,
        loginListener: LoginListener
    ) {
        this.loginListener = loginListener
        initializeGoogleSignInLauncher(fragment);
        googleSignInClient = GoogleSignIn.getClient(fragment.requireContext(),gso)
        googleSignInClient.signOut()
        val view = LayoutInflater.from(fragment.context).inflate(R.layout.google_registration, null)

        loginButton = view.findViewById(R.id.sign_in_button)
        loginButton.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)

        }

        container.addView(view)
    }

    private fun initializeGoogleSignInLauncher(fragment: Fragment) {
        googleSignInLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onActivityResult(RC_SIGN_IN, result.resultCode, result.data)
            }
    }

    private fun onActivityResult(rcSignIn: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data)
            sendLoginRequestToServer(account.result)
        } else {
            loginListener.onFailedLogin("Google Sign-in failed. Result code: $resultCode")
        }

    }

    private fun sendLoginRequestToServer(account: GoogleSignInAccount)
    {
        val reqBody = LoginBody(
            account.email.toString(),
            account.id.toString()
        )
        var loginRequestListener = LoginRequestHandler(reqBody)
        loginRequestListener.sendRequest(object :
            ResponseListener<com.airstrike.web_services.models.responses.LoggedInUser> {
            override fun onSuccess(response: com.airstrike.web_services.models.responses.LoggedInUser) {
                Log.i("Registered user", response.customer.toString())
                loginListener.onSuccessfulLogin( com.airstrike.core.authentification.LoggedInUser(
                    response.status!!,
                    response.success.toString(),
                    response.accessToken!!,
                    response.refreshToken!!,
                    response.customer?.email.toString(),
                    response.customer?.firstName.toString(),
                    response.customer?.lastName.toString(),
                    response.customer?.password.toString(),
                    response.customer?.custom?.fields?.phoneNumber.toString()
                ))

            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    googleSignInClient.signOut()
                    loginListener.onFailedLogin(error)
                }
            }

            override fun onFailure(t: Throwable) {
                loginListener.onFailedLogin(t.message.toString())
            }
        })
    }


}