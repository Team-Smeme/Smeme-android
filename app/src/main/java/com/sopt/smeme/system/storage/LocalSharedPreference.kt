package com.sopt.smeme.system.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.ktx.BuildConfig
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sopt.smeme.system.storage.LocalStorage.Companion.ACCESS_TOKEN
import com.sopt.smeme.system.storage.LocalStorage.Companion.ID
import com.sopt.smeme.system.storage.LocalStorage.Companion.LOGIN_CHECKED
import com.sopt.smeme.system.storage.LocalStorage.Companion.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSharedPreference @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val dataStore: SharedPreferences =
        if (BuildConfig.DEBUG) context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            context,
            SOURCE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun save(data: LocalStorage.Data) {
        // user data //
        if (data is UserData) {
            dataStore.edit().run {
                putLong(ID, data.id ?: -1)
                putString(ACCESS_TOKEN, data.accessToken)
                putString(REFRESH_TOKEN, data.refreshToken)
                putBoolean(LOGIN_CHECKED, true)
            }
        }
    }

    fun get(): LocalStorage.Data {
        with(dataStore) {
            val accessToken = getString(ACCESS_TOKEN, null) ?: return LocalStorage.EMPTY

            return UserData(
                getLong(ID, -1),
                getString(ACCESS_TOKEN, null),
                getString(REFRESH_TOKEN, null)
            )
        }
    }

    fun isAuthenticated(): Boolean {
        with(dataStore) {
            return getBoolean(LOGIN_CHECKED, false)
        }
    }

    companion object {
        private const val SOURCE = "Smeme"
    }
}