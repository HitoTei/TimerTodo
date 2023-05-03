package com.example.timertodo.ui.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timertodo.utils.Task
import com.example.timertodo.utils.toFormattedLocalDateTimeString
import java.time.Duration
import java.time.LocalDateTime


@OptIn(ExperimentalFoundationApi::class) // For Modifier.animateItemPlacement
@Composable
fun TaskList(
    onGotoEditTask: (Task) -> Unit,
    checkedTaskList: List<Task>,
    uncheckedTaskList: List<Task>,
    onCheckedChange: (Task, Boolean) -> Unit,
    onCloseTask: (Task) -> Unit,
    currentTime: LocalDateTime,
    modifier: Modifier = Modifier
) {
    var showCheckedTask by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(uncheckedTaskList, key = { it.id }) { task ->
            TaskListTile(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth(),
                task = task,
                onGotoEditTask = onGotoEditTask,
                onCheckedChange = onCheckedChange,
                onCloseTask = onCloseTask,
                currentTime = currentTime
            )
        }
        items(1) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        showCheckedTask = !showCheckedTask
                    }
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (showCheckedTask) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                )
                Text(
                    text = "完了",
                    color = LocalContentColor.current
                )
            }
        }
        if (showCheckedTask) items(checkedTaskList, key = { it.id }) { task ->
            TaskListTile(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth(),
                task = task,
                onGotoEditTask = onGotoEditTask,
                onCheckedChange = onCheckedChange,
                onCloseTask = onCloseTask,
                currentTime = currentTime
            )
        }
    }
}


@Composable
fun TaskListTile(
    modifier: Modifier,
    task: Task,
    currentTime: LocalDateTime,
    onGotoEditTask: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit,
    onCloseTask: (Task) -> Unit
) {
    val timeLeft by remember(currentTime) {
        derivedStateOf {
            val timeLimit = task.timeLimit ?: return@derivedStateOf null
            val timeLeft = Duration.between(currentTime, timeLimit)
            val hours = String.format("%04d", timeLeft.toHours())
            val minutes = String.format("%02d", timeLeft.toMinutes() % 60)
            val seconds = String.format("%02d", timeLeft.seconds % 60)
            "$hours 時間 $minutes 分 $seconds 秒"
        }
    }
    Box(modifier = Modifier.clickable {
        onGotoEditTask(task)
    }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TaskTile(
                modifier = modifier,
                text = task.text,
                timeLimit = task.timeLimit?.toFormattedLocalDateTimeString(),
                timeLeft = timeLeft,
                checked = task.checked,
                onCheckedChange = { onCheckedChange(task, it) },
                onClose = { onCloseTask(task) }
            )
            Divider(modifier = Modifier.fillMaxWidth(0.9f))
        }
    }
}

@Preview
@Composable
fun TaskListPrev() {
    val uncheckedTaskList = listOf(
        Task(
            id = 0,
            text = "Todo",
            initialChecked = false
        ),
        Task(
            id = 1,
            text = "Todo2",
            initialChecked = false
        )
    ).toMutableStateList()
    val checkedTaskList = listOf(
        Task(
            id = 2,
            text = "Todo3",
            initialChecked = true
        ),
        Task(
            id = 3,
            text = "Todo4",
            initialChecked = true
        )
    )
    TaskList(
        onGotoEditTask = {},
        uncheckedTaskList = uncheckedTaskList,
        checkedTaskList = checkedTaskList,
        onCheckedChange = { task, checked ->
            uncheckedTaskList.find { it.id == task.id }?.let {
                it.checked = checked
            }
        },
        onCloseTask = { task ->
            uncheckedTaskList.remove(task)
        },
        currentTime = LocalDateTime.now()
    )
}