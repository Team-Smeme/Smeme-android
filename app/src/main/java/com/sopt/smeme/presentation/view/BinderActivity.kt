package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityBinderBinding
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

    }
}