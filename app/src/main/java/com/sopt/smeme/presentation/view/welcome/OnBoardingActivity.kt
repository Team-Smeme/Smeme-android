package com.sopt.smeme.presentation.view.welcome

import android.content.Intent
import androidx.activity.viewModels
import com.sopt.smeme.R
import com.sopt.smeme.Social
import com.sopt.smeme.business.viewmodel.Receptionist
import com.sopt.smeme.databinding.ActivityOnBoardingBinding
import com.sopt.smeme.presentation.view.TestActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity :
    ViewBoundActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    private val receptionist: Receptionist by viewModels()

    override fun listen() {
        binding.btnOnBoardingKakao.setOnClickListener {
            // ViewModel 로 인증 요청 처리 전담
            // -> KAKAO 외 소셜 혹은 자체 로그인 확장에 대비
            receptionist.handle(Social.KAKAO)

            // 인증 후 임시 페이지로 이동
            startActivity(Intent(this, TestActivity::class.java))
            finish()
        }
    }
}