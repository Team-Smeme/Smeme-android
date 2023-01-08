package com.sopt.smeme.bridge.agent.user

import com.sopt.smeme.bridge.agent.Agent
import com.sopt.smeme.bridge.controller.response.SimpleResponse

interface ProfileAgent: Agent {
    suspend fun create(nickname: String, introducing: String) : SimpleResponse
}