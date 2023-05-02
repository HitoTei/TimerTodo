package com.example.timertodo.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timertodo.data.TaskRepository
import com.example.timertodo.utils.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    var task = mutableStateOf<Task?>(null)
    fun getTaskFromId(id: Int) = viewModelScope.launch {
        task.value = repository.getTaskFromId(id)
    }
    fun saveTask(text: String, timeLimit: LocalDateTime?) = viewModelScope.launch {
        repository.update(Task(task.value?.id ?: 0, text, task.value?.checked ?: false, timeLimit))
    }
}