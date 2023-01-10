package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import com.sopt.smeme.SignInType
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import com.sopt.smeme.bridge.agent.user.SignInAgent
import com.sopt.smeme.bridge.controller.AuthConnection
import com.sopt.smeme.system.storage.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class Receptionist @Inject constructor(
    @InjectWay(Way.DEV) private val signInAgent: SignInAgent,
    @InjectWay(Way.DEV) private val localStorage: LocalStorage,
    val authConnection: AuthConnection
) : ViewModel(), ViewModelFrame {

    // onCompleted : 로그인 성공 후 행위
    // onError : 에러시 행위
    fun handle(
        type: SignInType.Type,
        onCompleted: (Boolean) -> Unit, // Mandatory
        onError: (Throwable) -> Unit = {}  // Optional
    ) {
        try {
            signInAgent.call(type, onCompleted, onError)
        } catch (t: Throwable) {
            onError(t)
            Timber.e(t)
        }
    }
}