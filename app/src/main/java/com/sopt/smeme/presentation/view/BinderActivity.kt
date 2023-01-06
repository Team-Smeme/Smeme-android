package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityBinderBinding

class BinderActivity : ViewBoundActivity<ActivityBinderBinding>(R.layout.activity_binder) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLoginActivity.setOnClickListener {
            val toTestPage = Intent(this, TestActivity::class.java)
            startActivity(toTestPage)
        }

        binding.btnHomeActivity.setOnClickListener {
            val toHomeActivity = Intent(this, HomeActivity::class.java)
            startActivity(toHomeActivity)
        }

    }
}