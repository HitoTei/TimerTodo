package com.example.timertodo.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    TaskList(
        taskLists,
        onCheckedChange = { task, checked ->
            taskLists.find { it.id == task.id }?.let {
                it.checked = checked
            }
        },
        onCloseTask = { task ->
            taskLists.remove(task)
        }
    )
}

@Composable
fun TaskList(
    taskList: List<Task>,
    onCheckedChange: (Task, Boolean) -> Unit,
    onCloseTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(taskList, key = { it.id }) { task ->
            TaskCard(
                text = task.text,
                checked = task.checked,
                onCheckedChange = { onCheckedChange(task, it) },
                onClose = { onCloseTask(task) }
            )
        }
    }
}
