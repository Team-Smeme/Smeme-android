package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.MyDiaryData
import retrofit2.http.GET

interface MyDiaryConnection {
    @GET("/api/v1/users/diaries")
    suspend fun getMyDiaries(): DataResponse<List<MyDiaryData>>
}