package com.example.timertodo.ui.todo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TodoListPrev() {
    val todoList = listOf(
        Todo(
            id = 0,
            text = "Todo",
            initialChecked = false
        ),
        Todo(
            id = 2,
            text = "Todo2",
            initialChecked = true
        )
    ).toMutableStateList()
    TodoList(todoList)
}

@Composable
fun TodoList(todoList: List<Todo>) {
    LazyColumn {
        items(todoList, key = { it.id }) { todo ->
            TodoCard(
                text = todo.text,
                checked = todo.checked,
                onCheckedChange = { todo.checked = it })
        }
    }
}
