package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.smeme.bridge.agent.opendiary.OdirLikeSender
import com.sopt.smeme.bridge.controller.response.OdirLikeData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OdirLikeProvider @Inject constructor(
    private val odirLikeSender: OdirLikeSender,
) : ViewModel() {
    private val _likeResult = MutableLiveData<OdirLikeData>()
    val likeResult: LiveData<OdirLikeData>
        get() = _likeResult
}