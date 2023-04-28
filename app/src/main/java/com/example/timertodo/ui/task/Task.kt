package com.example.timertodo.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDateTime
import java.util.UUID

class Task(
    val id: Int,
    val text: String,
    initialChecked: Boolean = false,
    val timeLimit: LocalDateTime? = null
) {
    var checked by mutableStateOf(initialChecked)

    constructor(
        text: String,
        initialChecked: Boolean = false,
        timeLimit: LocalDateTime? = null
    ) : this(UUID.randomUUID().hashCode(), text, initialChecked, timeLimit)
}

