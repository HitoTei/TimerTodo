package com.example.timertodo.ui.task

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class) // For Scaffold
@Preview
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Column(Modifier.verticalScroll(rememberScrollState())) {
        TaskList(
            listName = "未完了のタスク",
            taskList = taskViewModel.uncheckedTaskList,
            onCheckedChange = { task, checked ->
                taskViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { taskViewModel.closeTask(it) },
            modifier = Modifier.heightIn(max = (screenHeight - 56).dp)
        )
        TaskList(
            listName = "完了したタスク",
            taskList = taskViewModel.checkedTaskList,
            onCheckedChange = { task, checked ->
                taskViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { taskViewModel.closeTask(it) },
            modifier = Modifier.heightIn(max = (screenHeight - 56).dp)
        )
    }
}

