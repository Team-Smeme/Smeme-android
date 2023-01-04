package com.sopt.smeme.presentation.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivitySplashBinding
import com.sopt.smeme.presentation.view.BinderActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity

class SplashActivity : ViewBoundActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun listen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this, BinderActivity::class.java))
                finish()
            }, 2000)
    }
}