package com.sopt.smeme.presentation

import com.sopt.smeme.DiaryObserver

class DiaryBooleanObserver(private val onEventUnhandledContent: (Boolean) -> Unit) :
    DiaryObserver<Boolean> {
    override fun onChanged(t: Boolean?) {
        t?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}