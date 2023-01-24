package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.DiaryWriteConnection
import com.sopt.smeme.bridge.controller.request.DiaryWriteRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DiaryAgent @Inject constructor(
    private val diaryWriteConnection: DiaryWriteConnection
) : DiaryWriting {
    override suspend fun writeDiary(
        content: String,
        targetCode: String,
        topic: Int,
        isPublic: Boolean,
        onCompleted: (Long) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val response = diaryWriteConnection.postDiary(
                DiaryWriteRequest(
                    content = content,
                    targetLang = targetCode,
                    topicId = topic,
                    isPublic = isPublic
                )
            )

            if (response.isSuccessful()) {
                val result = response.data ?: throw SmemeException(500, "서버로 부터 정상적인 값이 오지 않았습니다.")
                val diaryId = response.data.diaryId
                onCompleted.invoke(diaryId)
            } else {
                throw SmemeException(response.status, response.message)
            }
        } catch (t: Throwable) {
            if (t is SmemeException) onError.invoke(t)
        }
    }
}