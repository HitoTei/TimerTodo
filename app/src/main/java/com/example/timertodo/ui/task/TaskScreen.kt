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
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timertodo.RoomApplication
import com.example.timertodo.utils.Task
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = hiltViewModel(), // TODO: repositoryをDIする
    onGotoEditTask: (Task) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(
            // BUG: skipHiddenState, skipPartiallyExpandedをtrueにすると、再起動時にクラッシュする
            // TODO: bottomSheet以外を使うことを検討
            bottomSheetState = SheetState(
                skipHiddenState = false,
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Hidden
            )
        )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "hogehoge") },
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
            val taskList by taskViewModel.taskList.collectAsState(initial = emptyList())
            TaskList(
                onGotoEditTask = { task ->
                    onGotoEditTask(task)
                },
                checkedTaskList = taskViewModel.checkedTaskList(taskList),
                uncheckedTaskList = taskViewModel.uncheckedTaskList(taskList),
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