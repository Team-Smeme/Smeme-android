package com.sopt.smeme

class DiaryBooleanObserver(private val onEventUnhandledContent: (Boolean) -> Unit) :
    DiaryObserver<Boolean> {
    override fun onChanged(t: Boolean?) {
        t?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}