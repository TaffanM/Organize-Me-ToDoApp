package com.example.organizeme.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.organizeme.R
import com.example.organizeme.fragment.HomeFragment
import com.example.organizeme.fragment.PomodoroFragment
import com.example.organizeme.fragment.ProfileFragment
import com.example.organizeme.fragment.ToDoFragment
import com.example.organizeme.helper.DarkLightMode
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        actionBar?.show()
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        bottomNav.background = null
        bottomNav.menu.getItem(2).isEnabled = false

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.navList -> {
                    loadFragment(ToDoFragment())
                    true
                }
                R.id.navTime -> {
                    loadFragment(PomodoroFragment())
                    true
                }
                R.id.navProfile -> {
                    loadFragment(ProfileFragment())
                    true
                } else -> {
                    false
                }
            }

        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}