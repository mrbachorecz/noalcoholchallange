package com.mrbachorecz.noalcohol

import com.mrbachorecz.noalcohol.DaysCalculator.calculateDaysPassed
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class MainCardUtilsTest {

    @Test
    fun testCalculateDaysPassed() {
        // given
        val today = LocalDate.now()
        val nineDaysAgo = today.minusDays(9).toString()

        // when
        val displayText = calculateDaysPassed(nineDaysAgo)

        // then
        assertEquals("No Alcohol: 9 days", displayText)
    }
}