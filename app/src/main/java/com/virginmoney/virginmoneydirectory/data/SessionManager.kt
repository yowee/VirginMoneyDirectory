package com.virginmoney.virginmoneydirectory.data

import android.content.Context

class SessionManager(private val context: Context) {

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveUserSession(userId: String, username: String) {
        editor.putString("userId", userId)
        editor.putString("email", username)
        // Add more session data as needed
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains("userId") && sharedPreferences.contains("email")
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}
