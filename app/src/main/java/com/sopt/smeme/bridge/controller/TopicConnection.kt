package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.TopicData
import retrofit2.http.GET

interface TopicConnection {
    @GET("/api/v1/categories/topic")
    suspend fun getRandom(): DataResponse<TopicData>
}