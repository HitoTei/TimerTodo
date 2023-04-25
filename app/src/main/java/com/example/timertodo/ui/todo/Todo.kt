package com.example.timertodo.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class Todo (val id: Int, val text: String, initialChecked: Boolean) {
    var checked by mutableStateOf(initialChecked)
}