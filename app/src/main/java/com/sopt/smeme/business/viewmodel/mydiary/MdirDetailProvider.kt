package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.MyDiaryConnection
import com.sopt.smeme.bridge.controller.response.MdirDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MdirDetailProvider @Inject constructor(
    private val mdirDetailConnection: MyDiaryConnection,
) : ViewModel() {
    private val _diary = MutableLiveData<MdirDetailData>()
    val diary: LiveData<MdirDetailData>
        get() = _diary

    fun requestGetDiary(
        id: Long,
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                val response = mdirDetailConnection.selectDiary(id)
                if (response.isSuccessful()) {
                    val result =
                        response.data ?: throw SmemeException(500, "서버로부터 데이터를 불러오지 못했습니다.")
                    _diary.value = result
                } else {
                    throw SmemeException(response.status, response.message)
                }
            } catch (t: Throwable) {
                onError.invoke(t)
            }

        }
    }

}