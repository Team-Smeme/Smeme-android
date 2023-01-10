package com.sopt.smeme.business.viewmodel

import androidx.lifecycle.ViewModel

//@HiltViewModel
class Step2ViewModel : ViewModel() {
//    private val papagoAPI: PapagoAPI
//) : ViewModel() {
//    fun translate(
//        text: String,
//        onCompleted: (String) -> Unit = {},
//        onError: (Throwable) -> Unit = {}
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            // suspend 호출
//            papagoAPI.transferPapago(
//                "ko", "en", text,
//                BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET
//            ).enqueue(object : Callback<TranslateResult> {
//                override fun onResponse(
//                    call: Call<TranslateResult>,
//                    response: Response<TranslateResult>
//                ) {
//                    if (response.isSuccessful) {
//                        var result = response.body()?.message?.result?.translatedText
//
//                        if (result != null) {
//                            onCompleted.invoke(result)
//                            Log.d("번역 성공", "변역된 일기:$result")
//                        } else {
//                            throw IllegalStateException("TODO : change to SmemeException")
//                        }
//                    } else {
//                        onError.invoke(IllegalStateException("번역이 정상적으로 수행되지 않았습니다. ${response.code()} : ${response.message()}"))
//                    }
//                }
//
//                override fun onFailure(call: Call<TranslateResult>, t: Throwable) {
//                    onError.invoke(t)
//                }
//            })
//        }
//    }
//
}