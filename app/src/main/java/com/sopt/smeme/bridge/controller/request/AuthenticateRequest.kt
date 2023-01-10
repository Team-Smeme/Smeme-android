package com.sopt.smeme.bridge.controller.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateRequest(
    @SerializedName("social")
    val social: String
)