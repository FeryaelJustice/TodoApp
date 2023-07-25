package com.feryaeldev.todoapp.ui.screen.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeldev.todoapp.data.model.Task
import com.feryaeldev.todoapp.domain.usecase.local.AddTaskUseCase
import com.feryaeldev.todoapp.domain.usecase.local.DeleteTaskUseCase
import com.feryaeldev.todoapp.domain.usecase.local.GetTasksUseCase
import com.feryaeldev.todoapp.domain.usecase.local.UpdateTaskUseCase
import com.feryaeldev.todoapp.ui.screen.tasks.TasksUIState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUIState> = getTasksUseCase().map(::Success).catch {
        Error(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    fun onCloseDialog() {
        _showDialog.postValue(false)
    }

    fun onShowDialog() {
        _showDialog.postValue(true)
    }

    fun onTaskCreated(task: Task) {
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }

    fun onTaskCheckedChange(task: Task) {
        val modifiedTask = task.copy(selected = !task.selected)
        viewModelScope.launch {
            updateTaskUseCase(modifiedTask)
        }
    }

    fun onTaskItemRemove(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}