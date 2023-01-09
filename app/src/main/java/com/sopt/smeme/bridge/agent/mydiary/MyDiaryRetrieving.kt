package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.bridge.controller.response.DataResponse
import com.sopt.smeme.bridge.controller.response.MyDiaryData

interface MyDiaryRetrieving {
    suspend fun getList(): DataResponse<List<MyDiaryData>>
}