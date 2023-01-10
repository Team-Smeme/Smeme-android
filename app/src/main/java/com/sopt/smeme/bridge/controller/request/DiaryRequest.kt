package com.sopt.smeme.bridge.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class DiaryWriteRequest(
    val content: String,
    val targetLang: String,
    val topicId: Int,
    val isPublic: Boolean
)