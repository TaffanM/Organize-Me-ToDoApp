package com.example.organizeme.helper

import androidx.appcompat.app.AppCompatDelegate

class DarkLightMode {
    companion object{
        fun initDarkOrLightMode() {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}