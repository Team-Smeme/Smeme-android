package com.sopt.smeme.bridge.controller.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DiaryId(
    val diaryId: Long
)