package com.example.timertodo.data

import com.example.timertodo.utils.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TaskRepository{
    fun getAll(): Flow<List<Task>>
    suspend fun getTaskFromId(id: Int): Task?
    suspend fun insert(task: Task)
    suspend fun delete(task: Task)
    suspend fun update(task: Task)
}

class TaskRepositoryImpl @Inject constructor(private val dao: TaskDao) : TaskRepository{
    override fun getAll() = dao.getAll()
    override suspend fun getTaskFromId(id: Int) = dao.getTaskFromId(id)
    override suspend fun insert(task: Task) = dao.insert(task)
    override suspend fun delete(task: Task) = dao.delete(task)
    override suspend fun update(task: Task) = dao.update(task)
}