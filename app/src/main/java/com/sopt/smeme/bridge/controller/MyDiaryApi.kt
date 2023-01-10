package com.sopt.smeme.bridge.controller

import retrofit2.http.Body
import retrofit2.http.GET

interface MyDiaryApi {
    @GET
    fun getDiaries(@Body request: RequestWriteDiaryDto): MyDiartResponse
}