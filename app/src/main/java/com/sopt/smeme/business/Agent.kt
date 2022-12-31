package com.sopt.smeme.business

interface Agent<RESULT> {
    fun execute(vararg inputs: String): RESULT
    fun urgentStop()
}