package ru.geekbrains.nasaapiproject.ui.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import ru.geekbrains.nasaapiproject.App

enum class SharedPrefKeys(val key: String) {
    THEME_COLOR("themeColor"),
}

enum class SharedPrefValue(val value: String) {
    THEME_LIGHT("light"),
    THEME_NIGHT("night")
}

class AppSharedPreferences {
    private val sharedPreferences: SharedPreferences = App.instance
        .getSharedPreferences("", Context.MODE_PRIVATE)

    fun saveLightTheme() {
        sharedPreferences.edit().putString(SharedPrefKeys.THEME_COLOR.key, "light").commit()
    }

    fun saveNightTheme() {
        sharedPreferences.edit().putString(SharedPrefKeys.THEME_COLOR.key, "night").commit()
    }

    fun getSavedTheme(): SharedPrefValue {
        val theme = sharedPreferences.getString(SharedPrefKeys.THEME_COLOR.key, "light") ?: "light"
        return when(theme) {
            SharedPrefValue.THEME_LIGHT.value ->  SharedPrefValue.THEME_LIGHT
            SharedPrefValue.THEME_NIGHT.value ->  SharedPrefValue.THEME_NIGHT
            else -> SharedPrefValue.THEME_LIGHT
        }
    }

}