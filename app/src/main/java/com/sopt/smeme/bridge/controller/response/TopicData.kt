package com.sopt.smeme.bridge.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class TopicData(
    val id: Int,
    val content: String
)
