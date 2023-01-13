package com.sopt.smeme.business.viewmodel.opendiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.OdirListConnection
import com.sopt.smeme.bridge.controller.response.OdirCategoryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProvider @Inject constructor(
    private val odirListConnection: OdirListConnection,
) : ViewModel() {
    private val _categories = MutableLiveData<OdirCategoryData>()
    val categories: LiveData<OdirCategoryData>
        get() = _categories

    fun getCategories(
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                val response = odirListConnection.getCategories()

                if (response.isSuccessful()) {
                    val result =
                        response.data ?: throw SmemeException(500, "서버로 부터 데이터를 불러오지 못했습니다.")
                    _categories.value = result
                } else {
                    throw SmemeException(response.status, response.message)
                }
            } catch (t: Throwable) {
                if (t is SmemeException) onError.invoke(t)
            }
        }
    }
}