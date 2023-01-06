package com.sopt.smeme.system.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.ktx.BuildConfig
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sopt.smeme.HomeAccess
import com.sopt.smeme.KakaoLoginAccess
import com.sopt.smeme.UserAccessType
import com.sopt.smeme.system.storage.LocalStorage.Companion.ACCESS_TOKEN
import com.sopt.smeme.system.storage.LocalStorage.Companion.ID
import com.sopt.smeme.system.storage.LocalStorage.Companion.KAKAO_ACCESS_EXPIRED
import com.sopt.smeme.system.storage.LocalStorage.Companion.KAKAO_ACCESS_TOKEN
import com.sopt.smeme.system.storage.LocalStorage.Companion.KAKAO_ID_TOKEN
import com.sopt.smeme.system.storage.LocalStorage.Companion.KAKAO_REFRESH_EXPIRED
import com.sopt.smeme.system.storage.LocalStorage.Companion.KAKAO_REFRESH_TOKEN
import com.sopt.smeme.system.storage.LocalStorage.Companion.LOGIN_CHECKED
import com.sopt.smeme.system.storage.LocalStorage.Companion.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSharedPreference @Inject constructor(
    @ApplicationContext context: Context
) : LocalStorage {
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

    override fun save(data: LocalStorage.Data) {
        when (data) {
            // user data //
            is UserData -> {
                dataStore.edit().run {
                    putLong(ID, data.id ?: -1)
                    putString(ACCESS_TOKEN, data.accessToken)
                    putString(REFRESH_TOKEN, data.refreshToken)
                    putBoolean(LOGIN_CHECKED, true)
                }.apply()
            }
            // oauth access data //
            is AccessData -> {
                dataStore.edit().run {
                    putString(KAKAO_ACCESS_TOKEN, data.accessToken)
                    putString(KAKAO_REFRESH_TOKEN, data.refreshToken)
                    putString(KAKAO_ID_TOKEN, data.idToken)
                    putLong(
                        KAKAO_ACCESS_EXPIRED,
                        data.accessExpired.toInstant().toEpochMilli()
                    )
                    putLong(
                        KAKAO_REFRESH_EXPIRED,
                        data.refreshExpired.toInstant().toEpochMilli()
                    )
                    putBoolean(LOGIN_CHECKED, true)
                }.apply()
            }
            is LocalStorage.EMPTY -> {
                throw Exception("wrong access for local data")
            }
        }
    }

    override fun get(accessType: UserAccessType): LocalStorage.Data {
        with(dataStore) {
            if (!getBoolean(LOGIN_CHECKED, false)) return LocalStorage.EMPTY

            when (accessType) {
                is KakaoLoginAccess -> return AccessData(
                    accessToken = getString(KAKAO_ACCESS_TOKEN, null) ?: "default",
                    refreshToken = getString(KAKAO_REFRESH_TOKEN, null) ?: "default",
                    accessExpired = Date(getLong(KAKAO_ACCESS_EXPIRED, -1)),
                    refreshExpired = Date(getLong(KAKAO_REFRESH_EXPIRED, -1)),
                    idToken = getString(KAKAO_ID_TOKEN, null)
                )

                is HomeAccess -> return UserData(
                    getLong(ID, -1),
                    getString(ACCESS_TOKEN, null),
                    getString(REFRESH_TOKEN, null)
                )
            }
        }
    }

    override fun isAuthenticated(): Boolean {
        with(dataStore) {
            return getBoolean(LOGIN_CHECKED, false)
        }
    }

    companion object {
        private const val SOURCE = "Smeme"
    }
}