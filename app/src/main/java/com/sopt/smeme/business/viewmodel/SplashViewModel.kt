package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.smeme.Event
import com.sopt.smeme.bridge.agent.InjectWay
import com.sopt.smeme.bridge.agent.Way
import com.sopt.smeme.system.storage.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @InjectWay(Way.DEV) private val localStorage: LocalStorage
) : ViewModel(), ViewModelFrame {
    private val _isSignedUser = MutableLiveData<Event<Boolean>>()
    val isSignedUser: LiveData<Event<Boolean>> get() = _isSignedUser

    init {
        checkSignedUser()
    }

    private fun checkSignedUser() {
        _isSignedUser.value = Event(localStorage.isAuthenticated())
    }
}