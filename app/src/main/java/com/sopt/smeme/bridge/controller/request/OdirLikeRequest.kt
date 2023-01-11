package com.sopt.smeme.bridge.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class OdirLikeRequest(
    val diaryId: Int
)