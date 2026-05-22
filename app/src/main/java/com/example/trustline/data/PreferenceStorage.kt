package com.example.trustline.data

import android.content.Context
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

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_trustline_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private val USER_DETAILS_KEY = stringPreferencesKey("user_details")
        private const val ACCESS_TOKEN_KEY = "access_token"
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
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
        sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
        context.dataStore.edit { preferences ->
            preferences.remove(USER_DETAILS_KEY)
        }
    }
}
