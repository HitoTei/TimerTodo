package com.example.timertodo.ui.task

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val _taskList = getTaskList().toMutableStateList()
    val taskList: List<Task>
        get() = _taskList

    val uncheckedTaskList: List<Task>
        get() = _taskList.filter { !it.checked }

    val checkedTaskList: List<Task>
        get() = _taskList.filter { it.checked }

    fun closeTask(task: Task) {
        _taskList.remove(task)
    }

    fun changeTaskChecked(task: Task, checked: Boolean) {
        _taskList.find { it.id == task.id }?.let {
            it.checked = checked
        }
    }

    fun addTask(task: Task) {
        _taskList.add(task)
    }
}

private fun getTaskList() = List(30) { i -> Task(i, "Task $i", false)}