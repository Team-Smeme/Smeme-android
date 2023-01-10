package com.sopt.smeme.bridge.controller

import kotlinx.serialization.Serializable

@Serializable
data class MyDiartResponse(
 val status: Int,
 val success: Boolean,
 val message:String,
 val data: MyDiaryData
)
@Serializable
data class MyDiaryData(
    val id: Int
)