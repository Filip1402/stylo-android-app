package com.airstrike.stylo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.airstrike.stylo.fragments.HomepageFragment
import com.airstrike.stylo.fragments.ShoeDetails

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    R.id.shopping_fragment_container,
                    HomepageFragment()
                )
            }
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.shopping_fragment_container)
                when(currentFragment)
                {
                    is ShoeDetails ->{
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
            replace(R.id.shopping_fragment_container, newFragment)
            addToBackStack(null)
        }
    }
}