package com.example.timertodo

import androidx.lifecycle.ViewModel
import com.example.timertodo.utils.Task

// データベースのインスタンスの取得などを行う
class MainViewModel : ViewModel() {
    private val _taskList = mutableListOf<Task>()
    fun getTaskFromId(id: Int): Task? {
        return _taskList.find { it.id == id }
    }

    fun saveTask(task: Task){
        _taskList.add(task)
    }
}
