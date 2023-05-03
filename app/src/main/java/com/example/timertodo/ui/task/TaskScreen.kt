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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timertodo.utils.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = hiltViewModel(),
    onGotoEditTask: (Task) -> Unit,
) {

    var currentTime by remember {
        mutableStateOf(LocalDateTime.now())
    }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalDateTime.now()
            delay(1000)
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipHiddenState = false,
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Hidden
            )
        )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            SnackbarHost(hostState) { snackbarData ->
                Snackbar(
                    snackbarData,
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            }
        },
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
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
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Closed ${it.text}")
                    }
                },
                currentTime = currentTime
            )
        }
    }

}