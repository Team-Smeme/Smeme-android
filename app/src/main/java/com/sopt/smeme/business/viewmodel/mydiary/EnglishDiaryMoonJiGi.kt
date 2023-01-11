package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sopt.smeme.business.policy.DiaryPolicy

class EnglishDiaryMoonJiGi : ViewModel(), DiaryMoonJiGi {
    val diary: MutableLiveData<String> = MutableLiveData("")
    val isDiarySuit: LiveData<Boolean> =
        Transformations.map(diary) { acceptToNextStep(it) }

    private val _isCompleteActive = MutableLiveData(false)
    val isCompleteActive get() = _isCompleteActive

    fun setNextState() {
        _isCompleteActive.value = (isDiarySuit.value == true)
    }

    fun setCompleteState() {
        _isCompleteActive.value = (isDiarySuit.value == true)
    }

    override fun acceptToNextStep(text: String) = DiaryPolicy.onlyAlphabetMoreThan10(text)
    override fun isActive(): Boolean = isCompleteActive.value ?: false
}