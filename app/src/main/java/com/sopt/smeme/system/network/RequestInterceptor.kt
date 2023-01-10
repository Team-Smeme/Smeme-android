package com.sopt.smeme.system.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
    val accessToken: String,
    val refreshToken: String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .header("Authorization", "$BEARER$accessToken")
                .build()
        )
    }

    companion object {
        private const val BEARER = "Bearer "
    }
}