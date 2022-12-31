package com.sopt.smeme.business.interrupt

import timber.log.Timber

interface Interrupt<RESULT> {
    fun sync(): Result<RESULT>
    fun async(): Unit

    interface Result<RESULT> {
        fun get(): RESULT
        fun isSucceed(): Boolean
        fun onError(t: Throwable) {
            Timber.e(t)
        }
    }
}