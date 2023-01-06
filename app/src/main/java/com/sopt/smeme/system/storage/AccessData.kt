package com.sopt.smeme.system.storage

import java.time.Instant
import java.util.*

class AccessData(
    val accessToken: String,
    val refreshToken: String,
    val accessExpired: Date,
    val refreshExpired: Date,
    val idToken: String?
) : LocalStorage.Data {
    override fun hasValue() = accessExpired.after(Date.from(Instant.now()))
}