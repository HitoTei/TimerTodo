package com.example.timertodo.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "task")
class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var text: String,
    @ColumnInfo(name = "checked") var _checked: MutableState<Boolean>,
    var timeLimit: LocalDateTime? = null
) {
    @get:Ignore
    var checked: Boolean
        get() = _checked.value
        set(value){ _checked.value = value}
    constructor(
        text: String,
        initialChecked: Boolean = false,
        timeLimit: LocalDateTime? = null
    ) : this(0, text, mutableStateOf(initialChecked), timeLimit)

    constructor(
        id: Int,
        text: String,
        initialChecked: Boolean = false,
        timeLimit: LocalDateTime? = null
    ) : this(id, text, mutableStateOf(initialChecked), timeLimit)
}

