package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.agent.response.InitializingProfileRequest
import com.sopt.smeme.bridge.controller.response.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProfileConnection : Connection {

    // TODO : accessToken interceptor
    @PATCH("/api/v1/users")
    suspend fun initialize(
        @Body request: InitializingProfileRequest
    ): SimpleResponse
}