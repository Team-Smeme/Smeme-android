package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.mydiary.DiaryWriter
import com.sopt.smeme.bridge.controller.TopicConnection
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DiarySource2TargetManager @Inject constructor(
    private val diaryWriter: DiaryWriter,
    private val topicConnection: TopicConnection
) : ViewModel(), ViewModelFrame {
    private val _topic = MutableLiveData<Topic>()
    val topic: LiveData<Topic> get() = _topic

    fun getRandomTopic(
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.async {
            try {
                val response = topicConnection.getRandom()
                // 200
                if (response.isSuccessful() && response.data != null) {
                    _topic.value = Topic(response.data.content, response.data.id)
                } else {
                    throw SmemeException(
                        response.status,
                        "서버로 부터 올바른 응답이 오지 않았습니다: ${response.message}"
                    )
                }
            } catch (t: Throwable) {
                onError.invoke(t)
            }
        }
    }

    fun completeWriting(
        topicId: Int,
        content: String,
        languageCode: String,
        isPublic: Boolean,
        onCompleted: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.async {
            diaryWriter.writeDiary(
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