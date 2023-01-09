package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.bridge.agent.user.ProfileEditAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileInitializer @Inject constructor(
    private val profileEditAgent: ProfileEditAgent
) : ViewModel(), ViewModelFrame {

    /**
     * 최초 nickname 등록
     */
    fun initialize(
        nickname: String,
        introducing: String,
        onCompleted: () -> Unit = {},
        onError: (HttpException?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = profileEditAgent.create(nickname, introducing)
                if (response.isSuccessful()) {
                    onCompleted.invoke()
                } else onError.invoke(null)
            } catch (e: HttpException) {
                onError.invoke(e)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

}