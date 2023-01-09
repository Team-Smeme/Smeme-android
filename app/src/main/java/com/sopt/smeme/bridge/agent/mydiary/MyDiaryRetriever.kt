package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.bridge.controller.MyDiaryConnection
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@ViewModelScoped
class MyDiaryRetriever @Inject constructor(
    private val myDiaryConnection: MyDiaryConnection
) : MyDiaryRetrieving {

    override suspend fun getList() =
        CoroutineScope(Dispatchers.IO).async {
            myDiaryConnection.getMyDiaries()
        }.await()
}