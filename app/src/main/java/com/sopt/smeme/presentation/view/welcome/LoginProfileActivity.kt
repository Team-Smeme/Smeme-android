package com.sopt.smeme.presentation.view.welcome

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.sopt.smeme.R
import com.sopt.smeme.business.policy.ProfilePolicy
import com.sopt.smeme.business.viewmodel.ProfileInitializer
import com.sopt.smeme.databinding.ActivityLoginProfileBinding
import com.sopt.smeme.presentation.view.HomeActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginProfileActivity :
    ViewBoundActivity<ActivityLoginProfileBinding>(R.layout.activity_login_profile) {
    private val profileInitializer: ProfileInitializer by viewModels()

    override fun listen() {
        // text 의 작성 상태를 observe //
        binding.etLoginProfileNickname.addTextChangedListener(TextObserver())

        // 'x' 버튼 터치시 해당 et 의 text clear //
        binding.btnLoginProfileNicknameRemovable.setOnClickListener {
            clearNickname()
        }
        binding.btnLoginProfileIntroducingRemovable.setOnClickListener {
            clearIntroducing()
        }

        // nickname 조건이 맞으면 go next //
        binding.btnLoginProfileGoNext.setOnClickListener {
            val nickname = binding.etLoginProfileNickname.text.toString()
            val introducing = binding.etLoginProfileIntroducing.text.toString()

            if (ProfilePolicy.acceptNickname(nickname)) {
                profileInitializer.initialize(nickname, introducing,
                    onCompleted = {
                        val toMain = Intent(this, HomeActivity::class.java)
                        startActivity(toMain)
                        finish()
                    },
                    onError = {
                        runOnUiThread {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }
        }
    }

    private fun clearNickname() = binding.etLoginProfileNickname.text.clear()
    private fun clearIntroducing() = binding.etLoginProfileIntroducing.text.clear()

    // text watcher
    // -> nickname 이 정상적으로 작성되면 next 를 활성화시키기 위함
    private inner class TextObserver : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val toString = s.toString()
            if (s.isNullOrBlank()) {
                binding.btnLoginProfileGoNext.setImageResource(R.drawable.btn_welcome_not_active)
            }
            if (ProfilePolicy.acceptNickname(s.toString())) {
                binding.btnLoginProfileGoNext.setImageResource(R.drawable.btn_welcome_active)
            } else {
                binding.btnLoginProfileGoNext.setImageResource(R.drawable.btn_welcome_not_active)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }
}