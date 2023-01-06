package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SignInType
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.user.SignInAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Receptionist @Inject constructor(
    @InjectWay val signInAgent: SignInAgent
) : ViewModel(), ViewModelFrame {

    fun handle(type: SignInType.Type) {
        viewModelScope.launch {
            // 로그인 인증 요청 대리
            signInAgent.call(type)
        }
    }
}