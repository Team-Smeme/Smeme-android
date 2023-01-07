package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import com.sopt.smeme.SignInType
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import com.sopt.smeme.bridge.agent.user.SignInAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Receptionist @Inject constructor(
    @InjectWay(Way.MOCK) val signInAgent: SignInAgent
) : ViewModel(), ViewModelFrame {

    // onCompleted : 로그인 성공 후 행위
    // onError : 에러시 행위
    fun handle(
        type: SignInType.Type,
        onCompleted: () -> Unit, // Mandatory
        onError: () -> Unit = {}  // Optional
    ) {
        signInAgent.call(type, onCompleted, onError)
    }
}