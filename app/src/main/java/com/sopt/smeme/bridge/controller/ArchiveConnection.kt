package com.sopt.smeme.bridge.controller

import com.sopt.smeme.bridge.controller.response.ArchiveData
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.SimpleResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ArchiveConnection {
    @GET("/api/v1/scraps")
    suspend fun getArchives(): DataResponse<ArchiveData>

    @DELETE("/api/v1/scraps/{scrapId}")
    suspend fun remove(
        @Path("scrapId") id: Long
    ): SimpleResponse
}