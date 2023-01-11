package com.sopt.smeme.bridge.controller.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OdirCategoryData(
    @SerialName("categories")
    val categories: List<Category>,
) {
    @Serializable
    data class Category(
        @SerialName("id")
        val id: Int,
        @SerialName("content")
        val content: String,
    )
}