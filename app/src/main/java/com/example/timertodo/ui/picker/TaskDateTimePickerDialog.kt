package com.example.timertodo.ui.picker


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.timertodo.utils.toDatePickerState
import com.example.timertodo.utils.toTimePickerState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialLocalDateTime: LocalDate? = null
) {
    val state = initialLocalDateTime?.toDatePickerState() ?: rememberDatePickerState()
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
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("CANCEL")
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
    val state = initialLocalTime?.toTimePickerState() ?: rememberTimePickerState()
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
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("CANCEL")
            }
        }
    ) {
        TimePicker(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            state = state
        )
    }
}
