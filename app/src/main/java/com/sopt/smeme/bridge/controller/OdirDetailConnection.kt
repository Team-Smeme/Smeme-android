package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.request.OdirLikeRequest
import com.sopt.smeme.bridge.controller.request.OdirScrapRequest
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.OdirDetailData
import com.sopt.smeme.bridge.controller.response.OdirLikeData
import com.sopt.smeme.bridge.controller.response.OdirScrapData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OdirDetailConnection {
    // detail
    @GET("/api/v1/diaries/{diaryId}")
    fun getSelectedDiary(
        @Path("diaryId") diaryId: Int? = null
    ): DataResponse<OdirDetailData>

    // scrap
    @POST("/api/v1/scraps")
    suspend fun postScrap(
        @Body request: OdirScrapRequest
    ): DataResponse<OdirScrapData>

    // like
    @POST("/api/v1/diaries/like")
    suspend fun postLike(
        @Body request: OdirLikeRequest
    ): DataResponse<OdirLikeData>

}