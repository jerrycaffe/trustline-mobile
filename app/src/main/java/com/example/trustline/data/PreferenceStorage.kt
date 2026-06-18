package com.example.trustline.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "trustline_prefs")

class PreferenceStorage(private val context: Context) {

    private val masterKeyAlias = try {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    } catch (e: Exception) {
        Log.e("PreferenceStorage", "Error creating master key", e)
        null
    }

    private var sharedPreferences = try {
        createEncryptedPrefs()
    } catch (e: Exception) {
        Log.e("PreferenceStorage", "Error creating EncryptedSharedPreferences, clearing and retrying", e)
        clearCorruptedPrefs()
        createEncryptedPrefs()
    }

    private fun createEncryptedPrefs() = EncryptedSharedPreferences.create(
        "secure_trustline_prefs",
        masterKeyAlias ?: MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun clearCorruptedPrefs() {
        try {
            context.getSharedPreferences("secure_trustline_prefs", Context.MODE_PRIVATE).edit().clear().apply()
        } catch (e: Exception) {
            Log.e("PreferenceStorage", "Failed to clear corrupted prefs", e)
        }
    }

    companion object {
        private val USER_DETAILS_KEY = stringPreferencesKey("user_details")
        private const val ACCESS_TOKEN_KEY = "access_token"
    }

    fun saveAccessToken(token: String) {
        try {
            sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
        } catch (e: Exception) {
            Log.e("PreferenceStorage", "Error saving access token, clearing corrupted prefs", e)
            handleCryptoError()
        }
    }

    fun getAccessToken(): String? {
        return try {
            sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        } catch (e: Exception) {
            Log.e("PreferenceStorage", "Error reading access token, clearing corrupted prefs", e)
            handleCryptoError()
            null
        }
    }

    private fun handleCryptoError() {
        clearCorruptedPrefs()
        try {
            sharedPreferences = createEncryptedPrefs()
        } catch (e: Exception) {
            Log.e("PreferenceStorage", "Failed to recreate EncryptedSharedPreferences", e)
        }
    }

    suspend fun saveUserDetails(userDetails: UserDetails) {
        context.dataStore.edit { preferences ->
            preferences[USER_DETAILS_KEY] = Gson().toJson(userDetails)
        }
    }

    val userDetailsFlow: Flow<UserDetails?> = context.dataStore.data.map { preferences ->
        val json = preferences[USER_DETAILS_KEY]
        if (json != null) {
            Gson().fromJson(json, UserDetails::class.java)
        } else {
            null
        }
    }

    suspend fun clearAuthData() {
        try {
            sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
        } catch (e: Exception) {
            Log.e("PreferenceStorage", "Error clearing access token", e)
            handleCryptoError()
        }
        context.dataStore.edit { preferences ->
            preferences.remove(USER_DETAILS_KEY)
        }
    }
}
