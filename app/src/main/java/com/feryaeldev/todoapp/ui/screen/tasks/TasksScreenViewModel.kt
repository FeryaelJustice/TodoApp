package com.feryaeldev.todoapp.ui.screen.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeldev.todoapp.data.model.ApiResponse
import com.feryaeldev.todoapp.data.model.Task
import com.feryaeldev.todoapp.domain.usecase.local.AddTaskUseCase
import com.feryaeldev.todoapp.domain.usecase.local.GetTasksUseCase
import com.feryaeldev.todoapp.domain.usecase.network.GetApiResponseUseCase
import com.feryaeldev.todoapp.ui.screen.tasks.TasksUIState.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val getApiResponseUseCase: GetApiResponseUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    val gson: Gson
) : ViewModel() {

    /*
    Esto colocarlo en el val de iniciar variable si quisieramos tenerlo de otra forma, sino la que hay puesta:
    val uiState = getTasksUseCase().map(::Success)
                                    .catch { Error(it) }
                                    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
    */
    private val _uiState: MutableStateFlow<TasksUIState> = MutableStateFlow(Loading)
    val uiState: StateFlow<TasksUIState> = _uiState.asStateFlow()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _tasks = MutableLiveData(
        listOf(
            Task(id = 0, name = "Task 1", selected = false),
            Task(id = 1, name = "Task 2", selected = false)
        )
    )
    val tasks: LiveData<List<Task>>
        get() = _tasks

    init {
        loadNetworkAPI()
    }

    private fun loadNetworkAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = Loading
            val response: Response<ApiResponse> = getApiResponseUseCase()
            _message.postValue(response.body()?.message ?: "No message")
            _uiState.value = Success(emptyList())
        }
    }

    fun onCloseDialog() {
        _showDialog.postValue(false)
    }

    fun onShowDialog() {
        _showDialog.postValue(true)
    }

    fun onTaskCreated(task: Task) {
        _tasks.postValue(_tasks.value?.plus(task))
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }

    fun onTaskCheckedChange(modifiedTask: Task) {
        _tasks.postValue(_tasks.value?.map { task ->
            if (task.id == modifiedTask.id) {
                task.copy(selected = !task.selected)
            } else {
                task
            }
        })
    }

    fun onTaskItemRemove(task: Task) {
        _tasks.postValue(_tasks.value?.minus(task))
    }
}