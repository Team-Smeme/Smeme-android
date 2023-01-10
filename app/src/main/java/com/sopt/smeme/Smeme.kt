package com.sopt.smeme

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.sopt.smeme.BuildConfig.KAKAO_API_KEY
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Smeme : Application() {
    override fun onCreate() {
        super.onCreate()

        setupTimber()
        KakaoSdk.init(this, KAKAO_API_KEY)
    }
}

private fun setupTimber() {
    if (BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree()) // 팀버 초기화
}
