package com.sopt.smeme.presentation.view

import com.kakao.sdk.user.UserApiClient
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityOnBoardingBinding
import timber.log.Timber

class OnBoardingActivity :
    ViewBoundActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    override fun listen() {
        binding.btnOnBoardingKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->

                println(token)

                if (error != null) {
                    Timber.e(error, "로그인 실패")
                } else if (token != null) {
                    Timber.i("로그인 성공")
                }
            }
        }
    }
}