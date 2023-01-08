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
import com.sopt.smeme.presentation.view.BinderActivity
import com.sopt.smeme.presentation.view.TestActivity
import com.sopt.smeme.presentation.view.ViewBoundActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginProfileActivity :
    ViewBoundActivity<ActivityLoginProfileBinding>(R.layout.activity_login_profile) {
    private val profileInitializer: ProfileInitializer by viewModels()

    override fun constructLayout() {

    }

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
                        val toMain = Intent(this, TestActivity::class.java)
                        startActivity(toMain)
                        finish()
                    },
                    onError = {
                        if (it == null) {
                            Toast.makeText(this, "정상적인 접근이 아닙니다.", Toast.LENGTH_SHORT).show()
                        }
                        if (it?.code() == 401) {
                            Toast.makeText(this, "정상적인 접근이 아닙니다 (${it.code()})", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                this,
                                "내부적인 오류입니다. 관리자에 문의하세요. (${it?.code()})",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        val toBinding = Intent(this, BinderActivity::class.java)
                        startActivity(toBinding)
                        finish()
                    })
            }
        }
    }

    override fun observe() {
        super.observe()
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
        override fun afterTextChanged(s: Editable?) {

        }

    }
}