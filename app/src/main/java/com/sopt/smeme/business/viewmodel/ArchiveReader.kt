package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.ArchiveConnection
import com.sopt.smeme.bridge.controller.response.ArchiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveReader @Inject constructor(
    private val archiveConnection: ArchiveConnection
) : ViewModel() {
    private val _archives = MutableLiveData<ArchiveData>()
    val archives: LiveData<ArchiveData>
        get() = _archives

    fun getList(
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val result = archiveConnection.getArchives()
                if (result.isSuccessful()) {
                    _archives.value = result.data ?: throw SmemeException(
                        500,
                        "서버로 부터 올바르지 못한 데이터를 받아왔습니다."
                    )
                }
            } catch (t: Throwable) {
                if (t is SmemeException) onError.invoke(t)
            }
        }
    }
}