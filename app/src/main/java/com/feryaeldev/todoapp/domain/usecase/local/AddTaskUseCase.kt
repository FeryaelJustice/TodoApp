package com.feryaeldev.todoapp.domain.usecase.local

import com.feryaeldev.todoapp.data.model.Task
import com.feryaeldev.todoapp.data.repository.local.TasksTodoRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val tasksTodoRepository: TasksTodoRepository) {
    suspend operator fun invoke(task: Task) = tasksTodoRepository.addTask(task)
}