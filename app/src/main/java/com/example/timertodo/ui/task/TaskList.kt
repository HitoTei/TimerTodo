package com.example.timertodo.ui.task

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TaskListPrev() {
    val taskLists = listOf(
        Task(
            id = 0,
            text = "Todo",
            initialChecked = false
        ),
        Task(
            id = 2,
            text = "Todo2",
            initialChecked = true
        )
    ).toMutableStateList()
    TaskList(taskLists)
}

@Composable
fun TaskList(taskList: List<Task>) {
    LazyColumn {
        items(taskList, key = { it.id }) { task ->
            TaskCard(
                text = task.text,
                checked = task.checked,
                onCheckedChange = { task.checked = it })
        }
    }
}
