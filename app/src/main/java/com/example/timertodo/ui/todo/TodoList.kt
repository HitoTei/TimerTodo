package com.example.timertodo.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TodoListPrev() {
    val todoList = listOf("Todo1", "Todo2", "Todo3")
    TodoList(todoList)
}

@Composable
fun TodoList(todoList: List<String>) {
    LazyColumn() {
        items(todoList) { todo ->
            TodoCard(text = todo)
        }
    }
}
