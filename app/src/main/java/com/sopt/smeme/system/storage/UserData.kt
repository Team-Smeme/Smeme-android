package com.sopt.smeme.system.storage

class UserData(
    val id: Long?,
    val accessToken: String?,
    val refreshToken: String?
) : LocalStorage.Data {
    override fun hasValue() = accessToken != null && !accessToken.isBlank()
}