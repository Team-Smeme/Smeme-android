package com.sopt.smeme

import java.time.LocalDateTime
import java.util.regex.Pattern

object DateUtil {
    private const val YYYY_MM_DD_HH_MM =
        "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]|60])"

    fun asObject(yyyyMMddHHmm: String): LocalDateTime {
        if (Pattern.compile(YYYY_MM_DD_HH_MM).matcher(yyyyMMddHHmm).matches()) {
            return LocalDateTime.of(
                yyyyMMddHHmm.substring(0, 4).toInt(),
                yyyyMMddHHmm.substring(5, 7).toInt(),
                yyyyMMddHHmm.substring(8, 10).toInt(),
                yyyyMMddHHmm.substring(11, 13).toInt(),
                yyyyMMddHHmm.substring(14).toInt()
            )
        }

        throw IllegalArgumentException("wrong date format")
    }

    fun asString(date: LocalDateTime): String {
        val hour = if (date.hour / 10 >= 1) date.hour.toString() else "0${date.hour}"
        val minute = if (date.minute != 0) date.minute.toString() else "00"

        return "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ${hour}:${minute}"
    }
}