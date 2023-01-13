package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import com.sopt.smeme.bridge.agent.user.ProfileEditAgent
import com.sopt.smeme.system.storage.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileInitializer @Inject constructor(
    private val profileEditAgent: ProfileEditAgent,
    @InjectWay(Way.DEV) private val localStorage: LocalStorage
) : ViewModel(), ViewModelFrame {

    /**
     * 최초 nickname 등록
     */
    fun initialize(
        nickname: String,
        introducing: String,
        onCompleted: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                profileEditAgent.create(
                    nickname,
                    introducing,
                    localStorage.getAccessToken(),
                    onCompleted,
                    onError
                )
            } catch (t: Throwable) {
                if (t is SmemeException) onError.invoke(t)
            }
        }
    }

}