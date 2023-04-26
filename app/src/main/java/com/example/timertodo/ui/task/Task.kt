package com.example.timertodo.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Task (val id: Int, val text: String, initialChecked: Boolean) {
    var checked by mutableStateOf(initialChecked)
}

