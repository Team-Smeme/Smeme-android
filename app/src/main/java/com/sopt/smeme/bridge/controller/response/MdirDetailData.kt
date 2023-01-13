package com.sopt.smeme.bridge.controller.response

import com.sopt.smeme.DateUtil

@kotlinx.serialization.Serializable
data class MdirDetailData(
    val content: String,
    val topic: String,
    val topicId: Long,
    val category: String,
    val isPublic: Boolean,
    val createdAt: String,
    val likeCnt: Int
) : DateProvider {
    override fun createdAt() = DateUtil.asObject(createdAt)
}