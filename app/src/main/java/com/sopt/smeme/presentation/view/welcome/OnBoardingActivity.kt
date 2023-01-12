package com.sopt.smeme.presentation.view.welcome

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.sopt.smeme.R
import com.sopt.smeme.Social
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import com.sopt.smeme.business.viewmodel.Receptionist
import com.sopt.smeme.databinding.ActivityOnBoardingBinding
import com.sopt.smeme.presentation.view.home.HomeActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity
import com.sopt.smeme.system.storage.LocalStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity :
    ViewBoundActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    private val receptionist: Receptionist by viewModels()

    @Inject
    @InjectWay(Way.DEV)
    lateinit var localStorage: LocalStorage

    override fun listen() {
        binding.btnOnBoardingKakao.setOnClickListener {
            // ViewModel 로 인증 요청 처리 전담
            // -> KAKAO 외 소셜 혹은 자체 로그인 확장에 대비
            receptionist.handle(
                type = Social.KAKAO,
                onCompleted = {
                    // isRegistered true
                    if (it) {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    // isRegistered false -> Profile
                    else {
                        startActivity(Intent(this, LoginProfileActivity::class.java))
                    }
                    finish()
                },
                onError = {
                    runOnUiThread {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
        }
    }

}