package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.DateUtil
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.MyDiaryConnection
import com.sopt.smeme.bridge.model.MyDiary
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.time.LocalDate
import javax.inject.Inject

@ViewModelScoped
class MyDiaryRetriever @Inject constructor(
    private val myDiaryConnection: MyDiaryConnection
) : MyDiaryRetrieving {

    override suspend fun getList(date: LocalDate) =
        CoroutineScope(Dispatchers.IO).async {
            val response = myDiaryConnection.getMyDiaries(
                //DateUtil.asStringOnlyDate(date)
            )

            if (!response.isSuccessful()) {
                throw SmemeException(response.status, response.message)
            }

            val diaries = response.data?.diaries ?: emptyList()

            diaries.map {
                MyDiary(it.diaryId, DateUtil.asStringOnlyTime(it.createdAt()), it.content)
            }
        }.await()
}