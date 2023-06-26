package com.feryaeldev.todoapp.ui.screen.tasks

import com.feryaeldev.todoapp.data.model.Task

sealed interface TasksUIState {
    object Loading : TasksUIState
    data class Error(val error: Throwable) : TasksUIState
    data class Success(val data: List<Task>) : TasksUIState
}