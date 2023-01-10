package com.sopt.smeme.bridge.controller

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RequestWriteDiaryDto(
    @SerialName("content")
    val content : String,
    @SerialName("targetLang")
    val targetLang : String,
    @SerialName("topic")
    val topic : String,
    @SerialName("isPublic")
    val isPublic: Boolean
)
