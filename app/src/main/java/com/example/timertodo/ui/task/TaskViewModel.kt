package com.example.timertodo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timertodo.data.TaskRepository
import com.example.timertodo.utils.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val taskList = repository.getAll()
    fun uncheckedTaskList(taskList: List<Task>): List<Task>
         = taskList.filter { !it.checked }

    fun checkedTaskList(taskList: List<Task>): List<Task>
         = taskList.filter { it.checked }

    fun closeTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun changeTaskChecked(task: Task, checked: Boolean) {
        viewModelScope.launch {
            task.checked = checked
            repository.update(task)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

}
