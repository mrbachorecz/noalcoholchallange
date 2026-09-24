package com.mrbachorecz.noalcohol

import com.mrbachorecz.noalcohol.maincard.DaysCalculator
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassedMessage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class MainCardUtilsTest {

    @Test
    fun testCalculateDaysPassedMessage() {
        val today = LocalDate.now()
        val nineDaysAgo = today.minusDays(9).toString()

        val displayText = calculateDaysPassedMessage(nineDaysAgo)

        assertEquals("No Alcohol: 9 days", displayText)
    }

    @Test
    fun corruptDateReturnsZeroDays() {
        assertEquals(0, calculateDaysPassed("not-a-date"))
        assertEquals(0, calculateDaysPassed(""))
        assertEquals("No Alcohol: 0 days", calculateDaysPassedMessage("garbage"))
        assertNull(DaysCalculator.parseStoredDate("2024-13-40"))
    }

    @Test
    fun futureDateReturnsZeroDays() {
        val tomorrow = LocalDate.now().plusDays(1).toString()
        assertEquals(0, calculateDaysPassed(tomorrow))
    }
}
