package com.sopt.smeme.presentation

import com.sopt.smeme.business.viewmodel.mydiary.Topic

data class Diary(
    val topic: Topic,
    val isPublic: Boolean,
    val sourceDiaryContent: String,
    val isTopicSelected: Boolean,
    val translatedText: String
): java.io.Serializable
