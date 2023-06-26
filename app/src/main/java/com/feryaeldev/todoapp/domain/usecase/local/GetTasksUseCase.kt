package com.feryaeldev.todoapp.domain.usecase.local

import com.feryaeldev.todoapp.domain.repository.local.TasksTodoRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksTodoRepository: TasksTodoRepository){
    operator fun invoke() = tasksTodoRepository.tasks
}