package com.example.timertodo.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timertodo.data.TaskRepository
import com.example.timertodo.utils.Task
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EditTaskViewModel(id: Int, private val repository: TaskRepository) : ViewModel() {
    val task = repository.getTaskFromId(id)!! // 存在するIDが渡されるはずなのでnullチェックはしない

    fun saveTask(text: String, timeLimit: LocalDateTime?) = viewModelScope.launch {
        repository.update(Task(task.id, text, task.checked, timeLimit))
    }
}