package com.airstrike.stylo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.airstrike.stylo.fragments.CheckoutFragment
import com.airstrike.stylo.fragments.HomepageFragment
import com.airstrike.stylo.fragments.ShoeDetails
import com.airstrike.stylo.fragments.ShoppingCart

class ProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    R.id.processing_activity_fragment_container,
                    ShoppingCart()
                )
            }
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.shopping_fragment_container)
                when(currentFragment)
                {
                    is CheckoutFragment ->{
                        supportFragmentManager.popBackStack()
                    }
                    else ->{
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)
    }

    fun loadFragment(newFragment : Fragment)
    {
        supportFragmentManager.commit {
            replace(R.id.processing_activity_fragment_container, newFragment,"processing")
            addToBackStack(null)
        }
    }
}