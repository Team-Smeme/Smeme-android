package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirDetailConnection
import com.sopt.smeme.bridge.controller.response.OdirDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OdirDetailProvider @Inject constructor(
    private val odirDetailConnection: OdirDetailConnection,
) : ViewModel() {
    private val _diary = MutableLiveData<OdirDetailData>()
    val diary: LiveData<OdirDetailData>
        get() = _diary

    fun requestGetDiary(
        id: Int? = null,
        onError: (Throwable) -> Unit = {}
    ) {
        try {
            val response = odirDetailConnection.getSelectedDiary(id)
            // 200
            // 500 (throw server related error before getting data)
            if (response.isSuccessful()) {
                val result = response.data ?: throw SmemeException(response.status, response.message)
                _diary.value = result
            } else {
                // 400. 401
                throw SmemeException(response.status, response.message)
            }
            // etc error
        } catch (t: Throwable) {
            onError.invoke(t)
        }
    }
}