package com.feryaeldev.todoapp.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feryaeldev.todoapp.R
import com.feryaeldev.todoapp.ui.MainActivityViewModel
import com.feryaeldev.todoapp.ui.screen.tasks.TasksScreen
import kotlinx.coroutines.CoroutineScope

// Navigation Graph sections
const val todoapp = "todoapp" // login, register...
const val app = "app" // inside the account: tasks, settings...

// Screen Names
const val loginScreenRoute = "loginScreen"
const val registerScreenRoute = "registerScreen"
const val recoverAccountScreenRoute = "recoverAccountScreen"
const val tasksScreenRoute = "tasksScreen"

// Screen class
sealed class Screen(val route: String) {
    object LoginScreen : Screen(loginScreenRoute)
    object RegisterScreen : Screen(registerScreenRoute)
    object RecoverAccountScreen : Screen(recoverAccountScreenRoute)
    object TasksScreen : Screen(tasksScreenRoute)
}

// Main Navigation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    mainViewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val currentDestination = currentDestination(navController)
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TodoAppTopBar(
                navController = navController,
                currentDestination = currentDestination,
                context = context
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = todoapp,
            modifier = Modifier.padding(innerPadding)
        ) {
            graph(
                navController = navController,
                scope = scope,
                snackbarHostState = snackbarHostState,
                mainViewModel = mainViewModel,
                context = context
            )
        }
    }
}

fun NavGraphBuilder.graph(
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    mainViewModel: MainActivityViewModel,
    context: Context
) {
    navigation(startDestination = Screen.TasksScreen.route, route = todoapp) {
        composable(route = Screen.TasksScreen.route) {
            TasksScreen(
                navController = navController,
                scope = scope,
                snackbarHostState = snackbarHostState,
                mainActivityViewModel = mainViewModel,
                context = context
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppTopBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    context: Context
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.whatislovememe),
                contentDescription = "Logo",
                modifier = Modifier.clickable {
                    // navController.navigate(tasksScreenRoute)
                    Toast.makeText(context, "WHAT IS LOVE BEIBI DON JER MI", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        },
        navigationIcon = {
            if (currentDestination?.route != tasksScreenRoute) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    modifier = Modifier.clickable {
                        navController.navigateUp()
                        Toast.makeText(context, "Back press clicked", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.border(1.dp, Color.Black, ShapeDefaults.ExtraSmall)
    )
}

@Composable
fun currentDestination(navController: NavHostController): NavDestination? {
    return navController.currentBackStackEntry?.destination
}