package com.example.timertodo.ui.task

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
        listName = "Task",
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

@OptIn(ExperimentalFoundationApi::class) // For Modifier.animateItemPlacement
@Composable
fun TaskList(
    listName: String,
    taskList: List<Task>,
    onCheckedChange: (Task, Boolean) -> Unit,
    onCloseTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var showColumn by remember { mutableStateOf(true) }
    Column(modifier.animateContentSize()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            showColumn = !showColumn
        }.padding(16.dp).fillMaxWidth()) {
            Icon(
                imageVector = if (showColumn) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null
            )
            Text(text = listName)
        }

        if (showColumn) LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(taskList, key = { it.id }) { task ->
                TaskCard(
                    modifier = Modifier.animateItemPlacement(),
                    text = task.text,
                    checked = task.checked,
                    onCheckedChange = { onCheckedChange(task, it) },
                    onClose = { onCloseTask(task) }
                )
            }
        }
    }
}
