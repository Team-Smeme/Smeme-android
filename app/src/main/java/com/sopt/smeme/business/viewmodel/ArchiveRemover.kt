package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.ArchiveConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveRemover @Inject constructor(
    private val archiveConnection: ArchiveConnection
): ViewModel() {
    fun removeItem(id: Long, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val response = archiveConnection.remove(id)

                if(!response.isSuccessful()) throw SmemeException(500, "서버에서 처리하지 못한 요청입니다.")
            } catch (t: Throwable) {
                onError.invoke(t)
            }
        }
    }
}