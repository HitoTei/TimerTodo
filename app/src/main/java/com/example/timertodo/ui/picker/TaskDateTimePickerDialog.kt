package com.example.timertodo.ui.picker


import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialLocalDateTime: LocalDate? = null
) {
    val state = rememberDatePickerState(
        initialLocalDateTime?.atStartOfDay()?.toEpochSecond(ZoneOffset.UTC)
    )
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                val localDate = state.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                if (localDate != null)
                    onConfirm(localDate)
                onDismissRequest()
            }) {
                Text("決定")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("キャンセル")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    initialLocalTime: LocalTime? = null
) {
    val state = rememberTimePickerState(
        initialHour = initialLocalTime?.hour ?: 0,
        initialMinute = initialLocalTime?.minute ?: 0
    )
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,

        confirmButton = {
            Button(onClick = {
                val localTime = state.let {
                    LocalTime.of(it.hour, it.minute)
                }
                if (localTime != null)
                    onConfirm(localTime)
                onDismissRequest()
            }) {
                Text("決定")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("キャンセル")
            }
        }
    ) {
        TimePicker(state = state)
    }
}
