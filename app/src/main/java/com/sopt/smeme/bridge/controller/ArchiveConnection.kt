package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.response.ArchiveData
import com.sopt.smeme.bridge.controller.response.DataResponse
import retrofit2.http.GET

interface ArchiveConnection {
    @GET("/api/v1/scraps")
    suspend fun getArchives(): DataResponse<ArchiveData>
}