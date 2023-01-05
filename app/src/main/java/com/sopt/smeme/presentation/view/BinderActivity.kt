package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityBinderBinding

class BinderActivity : ViewBoundActivity<ActivityBinderBinding>(R.layout.activity_binder) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOnBoardingActivity.setOnClickListener {
            val toOnBoardingPage = Intent(this, OnBoardingActivity::class.java)
            startActivity(toOnBoardingPage)
        }
    }
}