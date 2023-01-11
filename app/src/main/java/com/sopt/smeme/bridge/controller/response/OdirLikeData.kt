package com.sopt.smeme.bridge.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class OdirLikeData(
    val hasLike: Boolean
)