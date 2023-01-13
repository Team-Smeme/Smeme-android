package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.bridge.controller.MyDiaryConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteMdir @Inject constructor(
    private val mdirDeleteConnection: MyDiaryConnection,
) : ViewModel() {

    fun deleteDiary(
        id: String,
        onError: (Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            try {
                val response = mdirDeleteConnection.deleteDiary(id)
                if (!response.isSuccessful()) {
                    return@launch
                }
            } catch (t: Throwable) {
                onError.invoke(t)
            }

        }
    }
}