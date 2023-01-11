package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirListConnection
import com.sopt.smeme.bridge.controller.response.OdirListData
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OdirListProvider @Inject constructor(
    private val odirListConnection: OdirListConnection,
) : ViewModel(), ViewModelFrame {
    private val _diaries = MutableLiveData<OdirListData>()
    val diaries: LiveData<OdirListData>
        get() = _diaries

    fun requestGetList(
        category: String? = null,
        onCompleted: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            try {

                val response = odirListConnection.getDiaries(category)
                if (response.isSuccessful()) {
                    val result =
                        response.data ?: throw SmemeException(500, "서버로 부터 데이터를 불러오지 못했습니다.")
                    _diaries.value = result
                } else {
                    throw SmemeException(response.status, response.message)
                }
            } catch (t: Throwable) {
                onError.invoke(t)
            }
        }
    }
}