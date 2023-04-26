package com.example.timertodo.ui.task

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class) // For Scaffold
@Preview
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    var shouldShowCheckedTask by rememberSaveable {
        mutableStateOf(true)
    }
    Scaffold(topBar = {
        TopAppBar(title = {}, actions = {
            IconButton(onClick = { shouldShowCheckedTask = !shouldShowCheckedTask }) {
                Icon(
                    painter = rememberVectorPainter(image = if (shouldShowCheckedTask) Icons.Default.Visibility else Icons.Default.VisibilityOff),
                    contentDescription = "Show/Hide"
                )
            }
        })
    }) {
        TaskList(
            modifier = Modifier.padding(it),
            taskList = taskViewModel.taskList,
            onCheckedChange = { task, checked ->
                taskViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                taskViewModel.closeTask(task)
            },
            shouldShowCheckedTask = shouldShowCheckedTask
        )

    }
}
