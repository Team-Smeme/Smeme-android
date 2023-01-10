package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.request.DiaryWriteRequest
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.DiaryId
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryWriteConnection {
    @POST("/api/v1/diaries")
    suspend fun postDiary(
        @Body request: DiaryWriteRequest
    ): DataResponse<DiaryId>
}