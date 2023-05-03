package com.example.timertodo.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timertodo.utils.toDatePickerState
import com.example.timertodo.utils.toTimePickerState
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    modifier: Modifier = Modifier,
    editTaskViewModel: EditTaskViewModel = hiltViewModel(),
    id: Int,
    onSaveClicked: () -> Unit
) {
    editTaskViewModel.getTaskFromId(id)
    val taskState by remember {
        editTaskViewModel.task
    }
    val task = taskState

    if (task == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        var text by remember { mutableStateOf(task.text) }
        var timeLimit by remember { mutableStateOf<LocalDateTime?>(task.timeLimit) }
        val datePickerState = timeLimit?.toLocalDate()?.toDatePickerState() ?: rememberDatePickerState()
        val timePickerState = timeLimit?.toLocalTime()?.toTimePickerState() ?: rememberTimePickerState()
        Column(
            modifier = modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("タスク名") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        timeLimit = if (timeLimit != null) null
                        else LocalDateTime.now()
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = if (timeLimit != null) "時間制限を解除" else "時間制限を設定")
                }

                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        if (timeLimit != null) {
                            val time = timePickerState.let { LocalTime.of(it.hour, it.minute) }
                            val date = datePickerState.let {
                                Instant.ofEpochMilli(it.selectedDateMillis ?: 0)
                                    .atZone(ZoneId.systemDefault()).toLocalDate()
                            }
                            timeLimit = LocalDateTime.of(date, time)
                        }
                        editTaskViewModel.saveTask(text, timeLimit)
                        onSaveClicked()
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "保存")
                }
            }
            if (timeLimit != null) {
                Column {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
