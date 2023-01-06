package com.sopt.smeme

sealed interface UserAccessType

object KakaoLoginAccess : UserAccessType
object HomeAccess : UserAccessType