package com.feryaeldev.todoapp.ui.screen.settings

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.feryaeldev.todoapp.ui.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    mainActivityViewModel: MainActivityViewModel,
    context: Context
) {
}