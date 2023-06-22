package com.feryaeldev.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feryaeldev.todoapp.data.model.Country
import com.feryaeldev.todoapp.domain.NetworkUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkUseCase: NetworkUseCase
) : ViewModel() {

    private val _countryLiveData = MutableLiveData<List<Country>?>()
    val countryList: LiveData<List<Country>?>
        get() = _countryLiveData

    init {
        loadAll()
    }

    private fun loadAll() {
        viewModelScope.launch(Dispatchers.IO) {
            // loadCountries()
        }
    }

    private suspend fun loadCountries() {
        _countryLiveData.postValue(networkUseCase.getCountries().body()?.countries)
    }
}