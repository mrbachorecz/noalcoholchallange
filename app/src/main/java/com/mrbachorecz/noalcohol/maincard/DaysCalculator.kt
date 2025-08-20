package com.mrbachorecz.noalcohol.maincard

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DaysCalculator {
    fun calculateDaysPassed(storedDate: String): String {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val startDate = LocalDate.parse(storedDate, formatter)
        val daysPassed = ChronoUnit.DAYS.between(startDate, LocalDate.now())
        return "No Alcohol: $daysPassed days"
    }
}