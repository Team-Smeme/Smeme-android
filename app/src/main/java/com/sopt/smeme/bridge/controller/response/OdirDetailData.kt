package com.sopt.smeme.bridge.controller.response

import com.sopt.smeme.DateUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OdirDetailData(
    val diaryId: Int,
    val content: String,
    val category: String,
    val topic: String,
    val likeCnt: Int,
    val createdAt: String,
    val userId: Int,
    val username: String,
    val bio: String,
    val hasLike: Boolean,
) : DateProvider {
    override fun createdAt() = DateUtil.asObject(createdAt)
}