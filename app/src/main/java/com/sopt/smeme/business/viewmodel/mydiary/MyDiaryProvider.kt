package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.DateUtil
import com.sopt.smeme.bridge.agent.mydiary.MockMyDiaryRetriever
import com.sopt.smeme.bridge.model.MyDiary
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyDiaryProvider @Inject constructor(
    private val myDiaryRetriever: MockMyDiaryRetriever
) : ViewModel(), ViewModelFrame {
    private val _dairies = MutableLiveData<List<MyDiary>>()
    val dairies: LiveData<List<MyDiary>> get() = _dairies

    fun requestGetList(
        onCompleted: () -> Unit = {},
        onError: (HttpException?) -> Unit = {}
    ) {
        viewModelScope.async {
            try {
                val response = myDiaryRetriever.getList()
                if (response.isSuccessful()) {
                    _dairies.value = myDiaryRetriever.getList().data?.map { it ->
                        MyDiary(
                            DateUtil.asString(it.createdAt()),
                            it.content
                        )
                    } ?: emptyList()
                } else onError.invoke(null)
            } catch (e: HttpException) {
                onError.invoke(e)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}