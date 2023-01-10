package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sopt.smeme.BuildConfig
import com.sopt.smeme.bridge.controller.PapagoAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class Step1ViewModel : ViewModel() {
    val diary: MutableLiveData<String> = MutableLiveData("")
    val isDiarySuit: LiveData<Boolean> =
        Transformations.map(diary) { isValidDiaryFormat(it) }

    val sourceDiary: MutableLiveData<String> = MutableLiveData()
    val content: LiveData<String> = Transformations.map(sourceDiary) { it }

    private val _isNextActive = MutableLiveData(false)
    val isNextActive get() = _isNextActive

    fun updateText(newText: String) {
        sourceDiary.value = newText
    }

    private fun isValidDiaryFormat(diary: String) = diary.trim().length >= 10

    fun setNextState(){
        _isNextActive.value = (isDiarySuit.value == true)
    }
}
