package com.phoneshield.app.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SessionManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPreferences = EncryptedSharedPreferences.create(
        context,
        "phone_shield_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
    }

    fun saveAuthToken(token: String) {
        encryptedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return encryptedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun saveUserId(userId: String) {
        encryptedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return encryptedPreferences.getString(KEY_USER_ID, null)
    }

    fun saveUserEmail(email: String) {
        encryptedPreferences.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return encryptedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null && getUserId() != null
    }

    fun clearSession() {
        encryptedPreferences.edit().clear().apply()
    }
}
