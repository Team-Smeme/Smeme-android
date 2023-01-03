package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import com.sopt.smeme.bridge.agent.TempAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val tempAgent: TempAgent
)
    : ViewModelFrame, ViewModel() {

}