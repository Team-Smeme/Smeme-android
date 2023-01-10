package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.SignInType
import com.sopt.smeme.bridge.agent.Agent

interface SignInAgent : Agent {
    fun call(
        type: SignInType.Type,
        onCompleted: (Boolean) -> Unit,
        onError: (RuntimeException) -> Unit
    )
}