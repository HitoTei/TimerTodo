package com.example.timertodo.ui.task

import AddTaskSheet
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(heightDp = 800)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = true,
                initialValue = SheetValue.Hidden
            )
        )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "TimerTodo") },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                    )
                }
            )
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddTaskSheet(
                    onConfirmed = { text, dateTime ->
                        scope.launch {
                            scaffoldState.bottomSheetState.hide()
                            scaffoldState.snackbarHostState.showSnackbar(
                                "Added $text ${
                                    if (dateTime != null) {
                                        "at $dateTime"
                                    } else {
                                        ""
                                    }
                                }"
                            )
                        }
                        taskViewModel.addTask(Task(text = text, timeLimit = dateTime))
                    },
                    onCanceled = {
                        scope.launch { scaffoldState.bottomSheetState.hide() }
                    }
                )
            }
        }) { _ ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            TaskList(
                checkedTaskList = taskViewModel.checkedTaskList,
                uncheckedTaskList = taskViewModel.uncheckedTaskList,
                onCheckedChange = { task, checked ->
                    taskViewModel.changeTaskChecked(task, checked)
                },
                onCloseTask = {
                    taskViewModel.closeTask(it)
                }
            )
        }
    }

}