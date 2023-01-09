package com.sopt.smeme

object TextUtil {
    fun wordControl(text: String): String {
        if (text.length <= 600) {
            return text
        } else {
            val viewing = text.substring(0, 601)
            val hiding = text.substring(601)

            return "$viewing ... (${text.length})"
        }
    }
}