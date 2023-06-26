package com.feryaeldev.todoapp.ui.screen.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeldev.todoapp.data.model.Task
import com.feryaeldev.todoapp.domain.NetworkUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val networkUseCase: NetworkUseCase,
    val gson: Gson
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    private val _tasks = MutableLiveData<List<Task>>(
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
            _loading.postValue(true)
            _message.postValue(networkUseCase.getApiResponse().body()?.message ?: "No message")
            _loading.postValue(false)
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