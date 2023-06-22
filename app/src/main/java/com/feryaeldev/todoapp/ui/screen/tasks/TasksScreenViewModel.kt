package com.feryaeldev.todoapp.ui.screen.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        loadAll()
    }

    private fun loadAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            _message.postValue(networkUseCase.getApiResponse().body()?.message ?: "No message")
            _loading.postValue(false)
        }
    }
}