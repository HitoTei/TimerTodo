package com.example.timertodo.ui.task

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    TaskList(
        taskList = taskViewModel.taskList,
        onCheckedChange = { task, checked ->
            taskViewModel.changeTaskChecked(task, checked)
        },
    )
}
