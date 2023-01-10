package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.bridge.agent.Agent

interface ProfileAgent : Agent {
    suspend fun create(
        nickname: String,
        introducing: String,
        token: String?,
        onCompleted: () -> Unit,
        onError: (Throwable) -> Unit
    )
}