package com.sopt.smeme.system.storage

interface LocalStorage {
    fun save(data: Data)
    fun get(): Data
    fun isAuthenticated(): Boolean

    interface Data {
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
    }
}