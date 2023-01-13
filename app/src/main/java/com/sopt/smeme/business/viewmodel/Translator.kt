package com.sopt.smeme.business.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sopt.smeme.BuildConfig
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.controller.PapagoAPI
import com.sopt.smeme.bridge.controller.TranslateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class Translator @Inject constructor(
    private val papagoAPI: PapagoAPI
) : ViewModel() {
    fun translate(
        text: String,
        sourceCode: String,
        targetCode: String,
        onCompleted: (String) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        papagoAPI.transferPapago(
            sourceCode, targetCode, text,
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
                    } else {
                        throw IllegalStateException("TODO : change to SmemeException")
                    }
                } else {
                    onError.invoke(IllegalStateException("번역이 정상적으로 수행되지 않았습니다. ${response.code()} : ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<TranslateResult>, t: Throwable) {
                if (t is SmemeException) onError.invoke(t)
            }
        })
    }
}


