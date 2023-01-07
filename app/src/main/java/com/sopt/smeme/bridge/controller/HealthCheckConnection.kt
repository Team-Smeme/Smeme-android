package com.sopt.smeme.bridge.controller

import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface HealthCheckConnection : Connection {
    @GET("/api/v1/test")
    suspend fun run(): Response
}

@Serializable
data class Response(
    val status: Int,
    val success: Boolean,
    val message: String
)