package com.example.timertodo.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "task")
class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val text: String,
    initialChecked: Boolean = false,
    val timeLimit: LocalDateTime? = null
) {
    var checked by mutableStateOf(initialChecked)

    constructor(
        text: String,
        initialChecked: Boolean = false,
        timeLimit: LocalDateTime? = null
    ) : this(0, text, initialChecked, timeLimit)
}

