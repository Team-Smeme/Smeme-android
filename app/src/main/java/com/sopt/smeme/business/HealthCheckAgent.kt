package com.sopt.smeme.business

import com.sopt.smeme.bridge.controller.Response

interface HealthCheckAgent {
    suspend fun call(): Response
}