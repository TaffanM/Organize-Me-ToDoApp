package com.example.organizeme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.organizeme.R
import com.example.organizeme.helper.DarkLightMode

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DarkLightMode.initDarkOrLightMode()
        setContentView(R.layout.activity_login)
    }
}