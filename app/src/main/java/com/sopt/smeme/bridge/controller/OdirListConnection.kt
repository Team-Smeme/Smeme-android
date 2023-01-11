package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.OdirCategoryData
import com.sopt.smeme.bridge.controller.response.OdirListData
import retrofit2.http.GET
import retrofit2.http.Query

interface OdirListConnection {
    // list
    @GET("/api/v1/diaries")
    suspend fun getDiaries(
        @Query("category") category: String? = null,
    ): DataResponse<OdirListData>

    // category
    @GET("/api/v1/categories")
    suspend fun getCategories(): DataResponse<OdirCategoryData>
}