package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.business.HealthCheckAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthChecker @Inject constructor(
    val healthCheckAgent: HealthCheckAgent
) : ViewModel() {

    private val _result = MutableLiveData<Int>()
    val result: LiveData<Int> get() = _result

    fun handle() {
        viewModelScope.launch {
            val response = healthCheckAgent.call()
            _result.value = response.status
        }
    }
}