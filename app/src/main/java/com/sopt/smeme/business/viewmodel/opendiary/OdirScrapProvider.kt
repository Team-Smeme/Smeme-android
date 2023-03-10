package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.opendiary.OdirScrapSender
import com.sopt.smeme.bridge.controller.response.OdirScrapData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OdirScrapProvider @Inject constructor(
    private val odirScrapSender: OdirScrapSender,
) : ViewModel() {
    private val _scrapResult = MutableLiveData<OdirScrapData>()
    val scrapResult: LiveData<OdirScrapData>
        get() = _scrapResult

    fun requestSendScrap (
        id: Int,
        paragraph: String,
        onCompleted: () -> Unit = {},
        onError: (RuntimeException?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = odirScrapSender.sendScrap(id, paragraph)
                _scrapResult.value = response
                onCompleted.invoke()
            } catch (t: Throwable) {
                if (t is SmemeException) onError.invoke(t)
            }
        }
    }
}