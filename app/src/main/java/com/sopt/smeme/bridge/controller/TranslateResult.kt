package com.sopt.smeme.bridge.controller

import com.google.gson.annotations.SerializedName

data class TranslateResult(
    @SerializedName("message")
    val message: Message
) {
    data class Message(
        @SerializedName("result")
        val result: Result
    ) {
        data class Result(
            @SerializedName("srcLangType")
            val srcLangType: String,
            @SerializedName("tarLangType")
            val tarLangType: String,
            @SerializedName("translatedText")
            val translatedText: String
        )
    }
}