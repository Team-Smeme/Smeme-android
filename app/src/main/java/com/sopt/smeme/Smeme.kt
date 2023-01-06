package com.sopt.smeme

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.sopt.smeme.BuildConfig.KAKAO_API_KEY
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Smeme : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, KAKAO_API_KEY)
    }
}