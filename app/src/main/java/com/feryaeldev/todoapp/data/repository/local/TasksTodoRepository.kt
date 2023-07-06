package com.feryaeldev.todoapp.data.repository.local

import com.feryaeldev.todoapp.data.db.TaskDao
import com.feryaeldev.todoapp.data.mapper.toTask
import com.feryaeldev.todoapp.data.mapper.toTaskEntity
import com.feryaeldev.todoapp.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksTodoRepository @Inject constructor(private val tasksDao: TaskDao) {

    val tasks: Flow<List<Task>> = tasksDao.getTasks().map { tasks -> tasks.map { it.toTask() } }

    suspend fun addTask(task: Task) = tasksDao.addTask(task.toTaskEntity())
}