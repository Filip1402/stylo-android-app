package com.airstrike.authentication_google

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.airstrike.core.authentification.RegisteredUser
import com.airstrike.core.authentification.RegistrationHandler
import com.airstrike.core.authentification.RegistrationListener
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.registration_google.R
import com.airstrike.web_services.models.RegistrationBody
import com.airstrike.web_services.request_handler.RegistrationRequestHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton


class GoogleRegistration : RegistrationHandler {

    private lateinit var registrationButton: SignInButton
    private val RC_SIGN_IN = 1001
    private lateinit var registrationListener: RegistrationListener
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>


    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestProfile()
        .build()

    override fun showUIandHandleRegistration(fragment: Fragment,container: LinearLayout,listener: RegistrationListener) {

        registrationListener = listener

        val view = LayoutInflater.from(fragment.context).inflate(R.layout.google_registration, null)

        initializeGoogleSignInLauncher(fragment);
        val googleSignInClient = GoogleSignIn.getClient(fragment.requireContext(),gso)
        googleSignInClient.signOut()

        val account =  GoogleSignIn.getLastSignedInAccount(fragment.requireContext())
        if(account  != null)
        {
            handleSignInResult(account)
        }
        registrationButton = view.findViewById(R.id.sign_in_button)
        registrationButton.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)

        }

        container.addView(view)
    }

    private fun handleSignInResult(account: GoogleSignInAccount) {
        val user = RegisteredUser(account.givenName!!, account.familyName!!, account.email.toString(), "", "")
        sendRegistrationRequestToServer(account)

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
            handleSignInResult(account.result)
        } else {
            Log.e("GoogleRegistration", "Sign-in failed. Result code: $resultCode")
        }

    }

    private fun sendRegistrationRequestToServer(account: GoogleSignInAccount)
    {
        val reqBody = RegistrationBody(
            account.givenName.toString(),
            account.familyName.toString(),
            account.email.toString(),
            "",
            account.id.toString()
        )
        var registrationRequestListener = RegistrationRequestHandler(reqBody)
        registrationRequestListener.sendRequest(object : ResponseListener<com.airstrike.web_services.models.responses.RegisteredUser> {
            override fun onSuccess(response: com.airstrike.web_services.models.responses.RegisteredUser) {
                Log.i("Registered user", response.customer.toString())
                registrationListener.onSuccessfulRegistration( com.airstrike.core.authentification.RegisteredUser(
                    account.givenName.toString(),
                    account.familyName.toString(),
                    account.email.toString(),
                    "",
                    account.id.toString()
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



}