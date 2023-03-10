package com.sopt.smeme.bridge.controller

import com.sopt.smeme.DateUtil
import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.MdirDetailData
import com.sopt.smeme.bridge.controller.response.MyDiaryData
import com.sopt.smeme.bridge.controller.response.SimpleResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface MyDiaryConnection {
    @GET("/api/v1/users/diaries")
    suspend fun getMyDiaries(
        @Query("date") date: String = DateUtil.WithServer.asStringOnlyDate(LocalDate.now())
    // @Query("date") date: String? = null
    ): DataResponse<MyDiaryData>

    @GET("/api/v1/users/diaries/{diaryId}")
    suspend fun selectDiary(
        @Path("diaryId") diaryId:Long
    ):DataResponse<MdirDetailData>

    @DELETE("/api/v1/diaries/{diaryId}")
    suspend fun deleteDiary(
        @Path("diaryId") diaryId:String
    ):SimpleResponse
}