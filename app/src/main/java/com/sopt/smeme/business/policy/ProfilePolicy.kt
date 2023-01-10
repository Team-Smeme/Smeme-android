package com.sopt.smeme.business.policy

object ProfilePolicy {
    // fun
    fun acceptNickname(text: String) = text.length > 0
}