package com.sopt.smeme.system.storage

import com.sopt.smeme.UserAccessType

interface LocalStorage {
    fun save(data: Data)
    fun get(accessType: UserAccessType): Data
    fun isAuthenticated(): Boolean

    sealed interface Data {
        fun hasValue(): Boolean
    }

    object EMPTY : Data {
        override fun hasValue() = false
    }

    companion object {
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        const val ID = "id"
        const val LOGIN_CHECKED = "loginChecked"

        // kakao //
        const val KAKAO_ACCESS_TOKEN = "kakaoAccessToken"
        const val KAKAO_REFRESH_TOKEN = "kakaoRefreshToken"
        const val KAKAO_ID_TOKEN = "kakaoIdToken"
        const val KAKAO_ACCESS_EXPIRED = "kakaoAccessExpired"
        const val KAKAO_REFRESH_EXPIRED = "kakaoRefreshExpired"
    }
}