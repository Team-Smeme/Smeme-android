package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.opendiary.OdirLikeSender
import com.sopt.smeme.bridge.controller.response.OdirLikeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OdirLikeProvider @Inject constructor(
    private val odirLikeSender: OdirLikeSender,
) : ViewModel() {
    private val _likeResult = MutableLiveData<OdirLikeData>()
    val likeResult: LiveData<OdirLikeData>
        get() = _likeResult

    fun requestSendLike (
        id: Int,
        onCompleted: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = odirLikeSender.sendLike(id)
                _likeResult.value = response
                onCompleted.invoke()
            } catch (e: SmemeException) {
                onError.invoke(e)
            } catch (e: HttpException) {
                onError.invoke(e)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}