package com.sopt.smeme

sealed class SignInType {
    sealed interface Type
}

object Social : SignInType() {
    object KAKAO : Type
    object NAVER : Type
}

object Own : SignInType() {
    object OWN : Type
}