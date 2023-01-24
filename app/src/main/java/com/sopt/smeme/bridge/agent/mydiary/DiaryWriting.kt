package com.sopt.smeme.bridge.agent.mydiary

interface DiaryWriting {
    suspend fun writeDiary(
        content: String,
        targetCode: String,
        topic: Int,
        isPublic: Boolean,
        onCompleted: (Long) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    )
}