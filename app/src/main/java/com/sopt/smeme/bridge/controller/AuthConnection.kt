package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.request.AuthenticateRequest
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.ServiceAccessData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthConnection {
    @POST("/api/v1/auth")
    suspend fun login(
        @Body request: AuthenticateRequest,
        @Header("Authorization") socialToken: String
    ): DataResponse<ServiceAccessData>
}