package com.mrbachorecz.noalcohol

import com.mrbachorecz.noalcohol.initialdate.parseDateOrToday
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class FancyDateInputTest {

    @Test
    fun testParseDateOrToday_valid() {
        val date = parseDateOrToday("2024-06-01")
        assertEquals(LocalDate.of(2024, 6, 1), date)
    }

    @Test
    fun testParseDateOrToday_invalid() {
        val today = LocalDate.now()
        val date = parseDateOrToday("")
        assertEquals(today, date)
    }
}