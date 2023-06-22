package com.feryaeldev.todoapp.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    mainViewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val currentDestination = currentDestination(navController)
    Scaffold(topBar = {
        TodoAppTopBar(
            navController = navController,
            currentDestination = currentDestination,
            context = context
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = todoapp,
            modifier = Modifier.padding(innerPadding)
        ) {
            graph(navController = navController, scope = scope, mainViewModel = mainViewModel)
        }
    }
}

fun NavGraphBuilder.graph(
    navController: NavHostController,
    scope: CoroutineScope,
    mainViewModel: MainActivityViewModel
) {
    navigation(startDestination = Screen.TasksScreen.route, route = todoapp) {
        composable(route = Screen.TasksScreen.route) {
            TasksScreen(
                navController = navController,
                coroutineScope = scope,
                mainActivityViewModel = mainViewModel
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
                    Toast.makeText(context, "Logo clicked", Toast.LENGTH_SHORT).show()
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
                    }
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun currentDestination(navController: NavHostController): NavDestination? {
    return navController.currentBackStackEntry?.destination
}