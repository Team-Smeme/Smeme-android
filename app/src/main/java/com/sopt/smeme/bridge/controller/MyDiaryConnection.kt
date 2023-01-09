package com.sopt.smeme.bridge.controller

import com.sopt.smeme.DateUtil
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.MyDiaryData
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface MyDiaryConnection {
    @GET("/api/v1/users/diaries")
    suspend fun getMyDiaries(
        @Query("date") date: String = DateUtil.asStringOnlyDate(LocalDate.now())
    ): DataResponse<List<MyDiaryData>>
}