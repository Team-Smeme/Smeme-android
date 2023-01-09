package com.sopt.smeme.bridge.controller.response

import java.time.LocalDateTime

interface DateProvider {
    fun createdAt(): LocalDateTime
}