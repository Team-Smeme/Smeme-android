package com.sopt.smeme.business.viewmodel.mydiary

interface DiaryMoonJiGi {
    fun acceptToNextStep(text: String): Boolean
    fun isActive(): Boolean
}