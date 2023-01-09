package com.sopt.smeme.bridge.controller.response

import com.google.gson.annotations.SerializedName
import com.sopt.smeme.DateUtil
import kotlinx.serialization.Serializable

@Serializable
data class MyDiaryData(
    @SerializedName(value = "diaryId")
    val id: Long,
    val content: String,
    val createdAt: String,
    val isPublic: Boolean,
    @SerializedName(value = "likeCnt")
    val likes: Long
) : DateProvider {
    override fun createdAt() = DateUtil.asObject(createdAt)
}