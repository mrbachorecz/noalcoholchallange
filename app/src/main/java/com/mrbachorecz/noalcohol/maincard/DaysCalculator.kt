package com.mrbachorecz.noalcohol.maincard

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

object DaysCalculator {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun calculateDaysPassedMessage(storedDate: String): String {
        val daysPassed = calculateDaysPassed(storedDate)
        val unit = if (daysPassed == 1) "day" else "days"
        return "No Alcohol: $daysPassed $unit"
    }

    fun calculateDaysPassed(storedDate: String): Int {
        val startDate = parseStoredDate(storedDate) ?: return 0
        return calculateDaysSinceThen(startDate)
    }

    /**
     * Returns a valid ISO local date, or null if missing/corrupt.
     */
    fun parseStoredDate(storedDate: String): LocalDate? {
        if (storedDate.isBlank()) return null
        return try {
            LocalDate.parse(storedDate, formatter)
        } catch (_: DateTimeParseException) {
            null
        }
    }

    private fun calculateDaysSinceThen(startDate: LocalDate): Int {
        val days = ChronoUnit.DAYS.between(startDate, LocalDate.now()).toInt()
        if (days < 0) return 0
        return days
    }
}
