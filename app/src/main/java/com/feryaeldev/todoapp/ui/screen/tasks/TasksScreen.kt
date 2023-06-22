package com.feryaeldev.todoapp.ui.screen.tasks

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.feryaeldev.todoapp.ui.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TasksScreen(
    viewModel: TasksScreenViewModel = hiltViewModel(),
    navController: NavController,
    coroutineScope: CoroutineScope,
    mainActivityViewModel: MainActivityViewModel
) {
    var loading = viewModel.loading.observeAsState()
    var msg = viewModel.message.observeAsState()
    if (loading.value == true) {
        CircularProgressIndicator()
    } else {
        Text(text = viewModel.gson.toJson(msg.value))
    }
}