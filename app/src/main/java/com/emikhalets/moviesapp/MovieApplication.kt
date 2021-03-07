package com.emikhalets.moviesapp

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val isNightMode = getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE)
                .getBoolean(SP_THEME, false)
        if (isNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        private const val SP_NAME = "shared_preferences_name"
        private const val SP_THEME = "shared_theme"
    }
}