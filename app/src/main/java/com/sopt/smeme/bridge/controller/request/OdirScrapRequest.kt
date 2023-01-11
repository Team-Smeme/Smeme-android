package com.sopt.smeme.bridge.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class OdirScrapRequest(
    val diaryId: Int,
    val paragraph: String
)