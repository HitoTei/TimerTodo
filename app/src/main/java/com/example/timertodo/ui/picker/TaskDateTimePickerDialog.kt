package com.example.timertodo.ui.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import java.time.LocalDateTime

fun TaskDatePickerDialog(context: Context, selectedDate: LocalDateTime, onDateChanged: (LocalDateTime) -> Unit): Dialog{
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            var newDateTime = selectedDate
            newDateTime = newDateTime.withYear(year)
            newDateTime = newDateTime.withMonth(month + 1)  // 数字が1つずれているので調整
            newDateTime = newDateTime.withDayOfMonth(dayOfMonth)
            onDateChanged(newDateTime)
        },
        selectedDate.year,
        selectedDate.monthValue - 1, // 数字が1つずれているので調整
        selectedDate.dayOfMonth
    )
}

fun TaskTimePickerDialog(context: Context, selectedDate: LocalDateTime, onTimeChanged: (LocalDateTime) -> Unit): Dialog{
    return TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            var newDate = selectedDate
            newDate = newDate.withHour(hourOfDay)
            newDate = newDate.withMinute(minute)
            onTimeChanged(newDate)
        },
        selectedDate.hour,
        selectedDate.minute,
        true
    )
}