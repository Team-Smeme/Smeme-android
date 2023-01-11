package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.bridge.agent.mydiary.DiaryAgent
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DiaryRegister @Inject constructor(
    private val diaryAgent: DiaryAgent
) : ViewModel(), ViewModelFrame {
    private val _topic = MutableLiveData<Topic>()
    val topic: LiveData<Topic> get() = _topic

    fun completeWriting(
        topicId: Int,
        content: String,
        languageCode: String,
        isPublic: Boolean,
        onCompleted: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.async {
            diaryAgent.writeDiary(
                content = content,
                targetCode = languageCode,
                topic = topicId,
                isPublic = isPublic,
                onCompleted = onCompleted,
                onError = onError
            )
        }
    }
}