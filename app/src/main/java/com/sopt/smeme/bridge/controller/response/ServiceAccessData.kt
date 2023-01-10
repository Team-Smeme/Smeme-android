package com.sopt.smeme.bridge.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class ServiceAccessData(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean
)
