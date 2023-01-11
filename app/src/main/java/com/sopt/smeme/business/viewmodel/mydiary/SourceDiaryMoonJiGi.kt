package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sopt.smeme.business.policy.DiaryPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceDiaryMoonJiGi @Inject constructor() : ViewModel(), DiaryMoonJiGi {
    val diary: MutableLiveData<String> = MutableLiveData("")
    val isDiarySuit: LiveData<Boolean> =
        Transformations.map(diary) { acceptToNextStep(it) }

    private val _isNextActive = MutableLiveData(false)
    val isNextActive get() = _isNextActive

    fun setNextState() {
        _isNextActive.value = (isDiarySuit.value == true)
    }

    override fun acceptToNextStep(text: String) = DiaryPolicy.textMoreThan10(text)
    override fun isActive() = isNextActive.value ?: false
}


