package com.sopt.smeme.bridge.controller.response

import com.google.gson.annotations.SerializedName
import com.sopt.smeme.DateUtil
import kotlinx.serialization.Serializable

@Serializable
data class MyDiaryData(
    val diaries: List<Detail>
) {
    @Serializable
    data class Detail(
        val diaryId: Long,
        val content: String,
        val createdAt: String,
        val isPublic: Boolean,
        val likeCnt: Long
    ) : DateProvider {
        override fun createdAt() = DateUtil.asObject(createdAt)
    }
}