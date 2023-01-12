package com.sopt.smeme.bridge.controller.response

import com.sopt.smeme.DateUtil
import kotlinx.serialization.Serializable

@Serializable
data class OdirListData(
    val diaries: List<Detail> = emptyList(),
) {
    @Serializable
    data class Detail(
        val diaryId: Int,
        val content: String,
        val likeCnt: Int,
        val userId: Int,
        val username: String,
        var isSeen: Boolean,
        val hasLike: Boolean,
        val createdAt: String,
    ) : DateProvider {
        override fun createdAt() = DateUtil.asObject(createdAt)
    }
}