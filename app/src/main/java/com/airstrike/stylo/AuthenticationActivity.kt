package com.airstrike.stylo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
    fun loadFragment(newFragment : Fragment)
    {
        var fragmentManager : FragmentManager= supportFragmentManager
        var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.auth_fragment_container,newFragment)
        transaction.commit()
    }
}