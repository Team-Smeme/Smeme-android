package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityBinderBinding
import com.sopt.smeme.presentation.view.odir.OdirDetailActivity
import com.sopt.smeme.presentation.view.welcome.LoginProfileActivity
import com.sopt.smeme.presentation.view.welcome.OnBoardingActivity

class BinderActivity : ViewBoundActivity<ActivityBinderBinding>(R.layout.activity_binder) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOnBoardingActivity.setOnClickListener {
            val toOnBoardingPage = Intent(this, OnBoardingActivity::class.java)
            startActivity(toOnBoardingPage)
        }

        binding.btnLoginProfileActivity.setOnClickListener {
            startActivity(Intent(this, LoginProfileActivity::class.java))
        }

        binding.btnHomeActivity.setOnClickListener {
            val toHomeActivity = Intent(this, HomeActivity::class.java)
            startActivity(toHomeActivity)
        }


        binding.btnOdirDetailActivity.setOnClickListener {
            val toDetailPage = Intent(this, OdirDetailActivity::class.java)
            startActivity(toDetailPage)
        }
        binding.btnWriteKorenDiary.setOnClickListener {
            val toWriteKoreanDiaryActivity = Intent(this, WriteDiaryStep1Activity::class.java)
            startActivity(toWriteKoreanDiaryActivity)
        }

    }
}