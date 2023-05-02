package com.example.timertodo.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timertodo.utils.toDatePickerState
import com.example.timertodo.utils.toTimePickerState
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    editTaskViewModel: EditTaskViewModel = hiltViewModel(),
    id: Int,
    onSaveClicked: () -> Unit
) {
    editTaskViewModel.getTaskFromId(id)
    val taskState by remember {
        editTaskViewModel.task
    }
    val task = taskState
    if(task == null){
        CircularProgressIndicator()
    }else {
        var text by remember { mutableStateOf(task.text) }
        var timeLimit by remember { mutableStateOf<LocalDateTime?>(task.timeLimit) }
        Column(
            modifier = Modifier.verticalScroll(state = rememberScrollState())
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("タスク名") }
            )

            Text(text = timeLimit.toString())

            Button(onClick = {
                timeLimit = if (timeLimit != null) null
                else LocalDateTime.now()
            }) {
                Text(text = if (timeLimit != null) "時間制限を解除" else "時間制限を設定")
            }

            Button(onClick = {
                editTaskViewModel.saveTask(text, timeLimit)
                onSaveClicked()
            }) {
                Text(text = "保存")
            }
            if (timeLimit != null) {
                Column {
                    DatePicker(state = timeLimit!!.toLocalDate().toDatePickerState())
                    TimePicker(state = timeLimit!!.toLocalTime().toTimePickerState())
                }
            }
        }
    }
}
