package com.sopt.smeme.bridge.agent.mydiary

import com.sopt.smeme.bridge.model.MyDiary
import java.time.LocalDate

interface MyDiaryRetrieving {
    suspend fun getList(date: LocalDate): List<MyDiary>
}