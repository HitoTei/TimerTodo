package com.example.timertodo.utils

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

internal fun LocalDateTime.withLocalDate(localDate: LocalDate): LocalDateTime {
    return withYear(localDate.year).withMonth(localDate.monthValue)
        .withDayOfMonth(localDate.dayOfMonth)
}

internal fun LocalDateTime.withLocalTime(localTime: LocalTime): LocalDateTime {
    return withHour(localTime.hour).withMinute(localTime.minute)
}

internal fun LocalDateTime.toFormattedLocalDateTimeString(showSeconds: Boolean = false): String {
    val pattern = if (showSeconds) "yyyy/MM/dd HH:mm:ss" else "yyyy/MM/dd HH:mm"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

internal fun LocalDateTime.toFormattedLocalDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}

internal fun LocalDateTime.toFormattedLocalTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocalTime.toTimePickerState(): TimePickerState {
    return rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LocalDate.toDatePickerState(): DatePickerState {
    return rememberDatePickerState(
        atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
    )
}