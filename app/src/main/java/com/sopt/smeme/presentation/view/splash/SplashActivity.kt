package com.sopt.smeme.presentation.view.splash

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sopt.smeme.EventObserver
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.SplashViewModel
import com.sopt.smeme.databinding.ActivitySplashBinding
import com.sopt.smeme.presentation.view.BinderActivity
import com.sopt.smeme.presentation.view.TestActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ViewBoundActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashCaller: SplashViewModel by viewModels()

    override fun listen() {
        splashCaller.isSignedUser.observe(this, EventObserver { isSigned ->
            lifecycleScope.launch {
                delay(2000)
                moveToNext(isSigned)
                finish()
            }
        })
    }

    private fun moveToNext(isSigned: Boolean) {
        startActivity(
            Intent(
                this@SplashActivity, if (isSigned) {
                    BinderActivity::class.java
                } else {
                    TestActivity::class.java
                }
            )
        )
    }
}