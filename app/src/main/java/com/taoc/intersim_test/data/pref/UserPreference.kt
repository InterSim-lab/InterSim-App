package com.taoc.intersim_test.data.pref


import android.content.Context
import android.media.Image
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val TOKEN = stringPreferencesKey("token")
        val FULL_NAME = stringPreferencesKey("full_name")
        val EMAIL = stringPreferencesKey("email")
        val PROFILE_IMAGE_URI = stringPreferencesKey("profile_image_uri")
    }

    suspend fun saveProfileImageUri(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_URI] = uri
        }
    }

    val profileImageUri: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_IMAGE_URI]
        }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun saveUserDetails(fullName: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[FULL_NAME] = fullName
            preferences[EMAIL] = email
        }
    }

    suspend fun savedToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    val token: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN]
        }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    val userDetails: Flow<Pair<String, String>> = context.dataStore.data
        .map { preferences ->
            Pair(
                preferences[FULL_NAME] ?: "No Name",
                preferences[EMAIL] ?: "No Email"
            )
        }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val REGISTERED_USERS = stringPreferencesKey("registered_users")

    suspend fun saveRegisteredUser(email: String, password: String) {
        context.dataStore.edit { preferences ->
            val existingUsers = preferences[REGISTERED_USERS] ?: ""
            val updatedUsers = "$existingUsers;$email:$password" // Format: email:password
            preferences[REGISTERED_USERS] = updatedUsers
        }
    }

    fun getRegisteredUsers(): Flow<Map<String, String>> {
        return context.dataStore.data.map { preferences ->
            val usersString = preferences[REGISTERED_USERS] ?: ""
            usersString.split(";")
                .filter { it.contains(":") }
                .associate {
                    val (email, password) = it.split(":")
                    email to password
                }
        }
    }
}