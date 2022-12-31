package com.sopt.smeme.business.interrupt

import com.sopt.smeme.Constants.Logger.Network
import timber.log.Timber

interface NetworkInterrupt : Interrupt<NetworkInterrupt.NetworkResult> {
    override fun sync(): NetworkResult
    override fun async()

    data class NetworkResult(
        val status: Int,
        val detail: String
    ) : Interrupt.Result<NetworkResult> {
        override fun get() = this
        override fun isSucceed() = status == 200
        override fun onError(t: Throwable) {
            Timber.tag(Network.TAG).e(t)
        }
    }
}