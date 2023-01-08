package com.sopt.smeme.bridge.controller.response

import kotlinx.serialization.Serializable

interface Response

@Serializable
data class DataResponse<DATA>(
    override var status: Int,
    val success: Boolean,
    val message: String,
    val data: DATA?
) : StatusChecker()

@Serializable
data class SimpleResponse(
    override var status: Int,
    val success: Boolean,
    val message: String?
) : StatusChecker()

abstract class StatusChecker {
    abstract var status: Int
    fun isSuccessful() = status - 200 in 0..19
}