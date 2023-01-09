package com.sopt.smeme

import org.junit.Test
import java.time.LocalDateTime

class DateUtilTest {
    @Test
    fun `String 에서 LocalDateTime 으로 변환한다`() {
        val 날짜_Object = DateUtil.asObject("2023-01-09 04:11")

        assert(날짜_Object.year == 2023)
        assert(날짜_Object.monthValue == 1)
        assert(날짜_Object.dayOfMonth == 9)
        assert(날짜_Object.hour == 4)
        assert(날짜_Object.minute == 11)
    }

    @Test
    fun `LocalDateTime 에서 String 으로 변환한다`() {
        assert(
            DateUtil.asString(LocalDateTime.of(2023, 1, 9, 4, 11))
                .equals("2023년 1월 9일 04:11")
        )

        assert(
            DateUtil.asString(LocalDateTime.of(2023, 11, 9, 23, 0))
                .equals("2023년 11월 9일 23:00")
        )

        assert(
            DateUtil.asString(LocalDateTime.of(2023, 11, 9, 0, 0))
                .equals("2023년 11월 9일 00:00")
        )

        assert(
            DateUtil.asString(LocalDateTime.of(2023, 11, 9, 3, 10))
                .equals("2023년 11월 9일 03:10")
        )
    }
}