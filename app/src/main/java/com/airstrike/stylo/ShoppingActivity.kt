package com.airstrike.stylo

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.airstrike.stylo.databinding.ActivityShoppingBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.commit
import com.airstrike.stylo.fragments.HomepageFragment
import com.airstrike.stylo.fragments.ShoeDetails


class ShoppingActivity : AppCompatActivity() {

    private lateinit var shoppingCartButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        shoppingCartButton = findViewById(R.id.shoppingCartButton)
        shoppingCartButton.setOnClickListener {
            val intent = Intent(this, ProcessingActivity::class.java)
            startActivity(intent)
        }

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