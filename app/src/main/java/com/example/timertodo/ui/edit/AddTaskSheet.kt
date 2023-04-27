package com.example.timertodo.ui.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.timertodo.ui.picker.TaskDatePickerDialog
import com.example.timertodo.ui.picker.TaskTimePickerDialog
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class) // For TextField
@Preview
@Composable
fun AddTaskScreen() {
//    var value by remember { mutableStateOf("") }
//
//    TextField(
//        value = value,
//        onValueChange = {value = it},
//        label = { Text("タスク") },
//        placeholder = { Text("タスクを入力してください") },
//        singleLine = true,
//        maxLines = 1,
//        modifier = Modifier.fillMaxWidth()
//    )

    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    val context = LocalContext.current
    Column {
        TextButton(onClick = {
            TaskDatePickerDialog(context, selectedDateTime) {
                selectedDateTime = it
            }.show()
        }) {
            Text(text = "${selectedDateTime.year}/${selectedDateTime.monthValue}/${selectedDateTime.dayOfMonth}")
        }
        TextButton(onClick = {
            TaskTimePickerDialog(context, selectedDateTime) {
                selectedDateTime = it
            }.show()
        }) {
            Text(text = "${selectedDateTime.hour}:${selectedDateTime.minute}")
        }
    }
}