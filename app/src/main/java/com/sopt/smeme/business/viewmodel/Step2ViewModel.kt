package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Step2ViewModel @Inject constructor(): ViewModel() {
    val diary: MutableLiveData<String> = MutableLiveData("")
    val isDiarySuit: LiveData<Boolean> =
        Transformations.map(diary) { isValidDiaryFormat(it) }

    private val _isCompleteActive = MutableLiveData(false)
    val isCompleteActive get() = _isCompleteActive

    //    val sourceDiary: MutableLiveData<String> = MutableLiveData()
    val content: LiveData<String> = Transformations.map(diary) { it }

    fun updateText(newText: String) {
        diary.value = newText
    }

    private fun isValidDiaryFormat(diary: String) : Boolean {
        if (diary.replace("[^a-zA-Z]".toRegex(),"").length >= 10) {
            return true
        }
        return false
    }

    fun setCompleteState() {
        _isCompleteActive.value = (isDiarySuit.value == true)
    }
}