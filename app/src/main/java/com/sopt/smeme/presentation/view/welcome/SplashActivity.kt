package com.sopt.smeme.presentation.view.welcome

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.sopt.smeme.EventObserver
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.SplashViewModel
import com.sopt.smeme.databinding.ActivitySplashBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ViewBoundActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashCaller: SplashViewModel by viewModels()

    override fun constructLayout() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
    }

    override fun listen() {
        splashCaller.isSignedUser.observe(this, EventObserver { isSigned ->
            lifecycleScope.launch {
                delay(1600)
                moveToNext(isSigned)
                finish()
            }
        })
    }

    private fun moveToNext(isSigned: Boolean) {
        startActivity(
            Intent(
                this@SplashActivity, if (isSigned) {
                    HomeActivity::class.java
                } else {
                    OnBoardingActivity::class.java
                }
            )
        )
    }
}