package com.sopt.smeme.system.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sopt.smeme.system.storage.LocalDataStore.PreferenceKeys.ACCESS_TOKEN
import com.sopt.smeme.system.storage.LocalDataStore.PreferenceKeys.ID
import com.sopt.smeme.system.storage.LocalDataStore.PreferenceKeys.LOGIN_CHECK
import com.sopt.smeme.system.storage.LocalDataStore.PreferenceKeys.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LocalDataStore(
    @ApplicationContext private val context: Context
) {

    private val Context.tokenDataStore by preferencesDataStore(TOKEN_DATASTORE)
    private val Context.loginCheckDataStore by preferencesDataStore(LOGIN_CHECK_DATASTORE)

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey(LocalStorage.ACCESS_TOKEN)
        val REFRESH_TOKEN = stringPreferencesKey(LocalStorage.REFRESH_TOKEN)
        val ID = longPreferencesKey(LocalStorage.ID)
        val LOGIN_CHECK = booleanPreferencesKey(LocalStorage.ID + LocalStorage.ACCESS_TOKEN)
    }

    companion object {
        private const val TOKEN_DATASTORE = "user_info_storage"
        private const val LOGIN_CHECK_DATASTORE = "login_switcher"
    }

    suspend fun save(accessToken: String, refreshToken: String, id: Long?) {
        context.tokenDataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = accessToken
            prefs[REFRESH_TOKEN] = refreshToken
            prefs[ID] = id ?: -1
        }

        context.loginCheckDataStore.edit { prefs ->
            prefs[LOGIN_CHECK] = true
        }
    }

    suspend fun get(): Flow<UserData> =
        context.tokenDataStore.data
            .catch {
                // TODO
            }
            .map { prefs ->
                UserData(prefs.get(ID), prefs[ACCESS_TOKEN], prefs[REFRESH_TOKEN])
            }

    suspend fun isAuthenticated() =
        context.loginCheckDataStore.data.map { pref -> pref[LOGIN_CHECK] ?: false }
}