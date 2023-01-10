package com.sopt.smeme.business.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sopt.smeme.BuildConfig
import com.sopt.smeme.bridge.controller.PapagoAPI
import com.sopt.smeme.bridge.controller.TranslateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class Step1ViewModel @Inject constructor(
    private val papagoAPI: PapagoAPI
) : ViewModel() {
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

    fun setNextState() {
        _isNextActive.value = (isDiarySuit.value == true)
    }

    fun translate(
        text: String,
        onCompleted: (String) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        papagoAPI.transferPapago(
            "ko", "en", text,
            BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET
        ).enqueue(object : Callback<TranslateResult> {
            override fun onResponse(
                call: Call<TranslateResult>,
                response: Response<TranslateResult>
            ) {
                if (response.isSuccessful) {
                    var result = response.body()?.message?.result?.translatedText

                    if (result != null) {
                        onCompleted.invoke(result)
                        Log.d("번역 성공", "변역된 일기:$result")
                    } else {
                        throw IllegalStateException("TODO : change to SmemeException")
                    }
                } else {
                    onError.invoke(IllegalStateException("번역이 정상적으로 수행되지 않았습니다. ${response.code()} : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<TranslateResult>, t: Throwable) {
                onError.invoke(t)
            }
        })
    }
}


