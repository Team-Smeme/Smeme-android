package com.sopt.smeme.business.viewmodel.mydiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.smeme.SmemeException
import com.sopt.smeme.bridge.agent.mydiary.MyDiaryRetriever
import com.sopt.smeme.bridge.model.MyDiary
import com.sopt.smeme.business.viewmodel.ViewModelFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import retrofit2.HttpException
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyDiaryProvider @Inject constructor(
    private val myDiaryRetriever: MyDiaryRetriever
) : ViewModel(), ViewModelFrame {
    private val _dairies = MutableLiveData<List<MyDiary>>()
    val dairies: LiveData<List<MyDiary>> get() = _dairies

    fun requestGetList(
        date: LocalDate,
        onCompleted: () -> Unit = {},
        onError: (RuntimeException?) -> Unit = {}
    ) {
        viewModelScope.async {
            try {
                val response = myDiaryRetriever.getList(date)
                _dairies.value = response
            } catch (e: SmemeException) {
                onError.invoke(e)
            } catch (e: HttpException) {
                onError.invoke(e)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}