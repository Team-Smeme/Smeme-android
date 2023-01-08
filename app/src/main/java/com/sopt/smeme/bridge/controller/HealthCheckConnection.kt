package com.sopt.smeme.bridge.controller

import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface HealthCheckConnection : Connection {
    @GET("/api/v1/test")
    suspend fun run(): Response<Any?>
}

@Serializable
data class Response<T>(
    val status: Int,
    val success: Boolean,
    val message: String
)