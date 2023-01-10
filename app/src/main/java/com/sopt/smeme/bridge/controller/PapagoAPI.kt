package com.sopt.smeme.bridge.controller

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface PapagoAPI {
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun transferPapago(
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret : String
    ): Call<TranslateResult>
}