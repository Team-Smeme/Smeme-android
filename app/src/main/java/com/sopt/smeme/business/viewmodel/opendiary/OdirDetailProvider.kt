package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirDetailConnection
import com.sopt.smeme.bridge.controller.response.OdirDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OdirDetailProvider @Inject constructor(
    private val odirDetailConnection: OdirDetailConnection,
) : ViewModel() {
    private val _diary = MutableLiveData<OdirDetailData>()
    val diary: LiveData<OdirDetailData>
        get() = _diary

    val isTopic = diary.value?.topic != ""

    fun requestGetDiary(
        id: Int,
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                val response = odirDetailConnection.getSelectedDiary(id)
                // 200
//              // 500 (throw server related error before getting data)
                if (response.isSuccessful()) {
                    val result =
                        response.data ?: throw SmemeException(500, "서버로부터 데이터를 불러오지 못했습니다.")
                    _diary.value = result
                } else {
                    // 400, 401
                    throw SmemeException(response.status, response.message)
                }
                // etc error
            } catch (t: Throwable) {
                onError.invoke(t)
            }
        }
    }


}