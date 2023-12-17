package com.airstrike.stylo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.airstrike.core.authentification.LoggedInUser
import com.airstrike.core.authentification.helpers.JwtHandler
import com.airstrike.stylo.helpers.SecurePreferencesManager

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isUserAlreadyLoggedIn())
        {
            val intent = Intent(this, ShoppingActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_authentication)
    }
    fun loadFragment(newFragment : Fragment)
    {
        var fragmentManager : FragmentManager= supportFragmentManager
        var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.auth_fragment_container,newFragment)
        transaction.commit()
    }

    private fun isUserAlreadyLoggedIn() : Boolean
    {
        val data  = SecurePreferencesManager(this).getObject("loggedInUser", LoggedInUser::class.java)
        if(data != null) {
            val user = data as LoggedInUser
            if(JwtHandler.isAccessTokenExpired(user.accessToken))
            {
                Toast.makeText(this, R.string.expired_login,Toast.LENGTH_LONG).show()
                return false
            }
            else
            {
                return true
            }
        }
        else
        {
            return false
        }

    }
}