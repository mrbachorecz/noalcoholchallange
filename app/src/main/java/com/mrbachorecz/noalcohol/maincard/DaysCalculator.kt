package com.mrbachorecz.noalcohol.maincard

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DaysCalculator {
    fun calculateDaysPassedMessage(storedDate: String): String {
        if (storedDate.isEmpty()) return "No Alcohol: 0 day"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val startDate = LocalDate.parse(storedDate, formatter)
        val daysPassed = calculateDaysSinceThen(startDate)
        val unit = if (daysPassed == 1) "day" else "days"
        return "No Alcohol: $daysPassed $unit"
    }

    fun calculateDaysPassed(storedDate: String): Int {
        if (storedDate.isEmpty()) return 0
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val startDate = LocalDate.parse(storedDate, formatter)
        return calculateDaysSinceThen(startDate)
    }

    private fun calculateDaysSinceThen(startDate: LocalDate?): Int {
        if (startDate == null) return 0
        val days = ChronoUnit.DAYS.between(startDate, LocalDate.now()).toInt()
        if (days < 0) return 0
        return days
    }
}