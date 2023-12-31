package com.feryaeldev.todoapp.ui.screen.tasks

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.feryaeldev.todoapp.ui.MainActivityViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.feryaeldev.todoapp.data.model.Task
import kotlinx.coroutines.CoroutineScope

@Composable
fun TasksScreen(
    viewModel: TasksScreenViewModel = hiltViewModel(),
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    mainActivityViewModel: MainActivityViewModel,
    context: Context
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<TasksUIState>(
        initialValue = TasksUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    val showDialog: Boolean by viewModel.showDialog.observeAsState(false)

    /*
    LaunchedEffect(key1 = uiState, block = {
        if (uiState is TasksUIState.Success) {
            scope.launch(Dispatchers.Main) {
                viewModel.message.value?.let { snackbarHostState.showSnackbar(it) }
            }
        }
    })
    */

    when (uiState) {
        is TasksUIState.Error -> {
            Text(text = "Error")
        }

        is TasksUIState.Loading -> {
            CircularProgressIndicator()
        }

        is TasksUIState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTasksDialog(
                    show = showDialog,
                    onDismiss = { viewModel.onCloseDialog() },
                    onTaskAdd = {
                        viewModel.onTaskCreated(it)
                        Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
                    }
                )
                TasksList(myTasks = (uiState as TasksUIState.Success).data, viewModel = viewModel)
                FabDialog(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    viewModel.onShowDialog()
                }
            }
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier = Modifier, onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdd: (Task) -> Unit) {
    var myTaskName by rememberSaveable { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add task",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTaskName, onValueChange = { myTaskName = it })
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onTaskAdd(Task(name = myTaskName, selected = false)); myTaskName = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Task")
                }
            }
        }
    }
}

@Composable
fun TasksList(myTasks: List<Task>, viewModel: TasksScreenViewModel) {
    // val myTasks: List<Task> = viewModel.tasks.observeAsState().value ?: emptyList()
    LazyColumn {
        items(myTasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onTaskCheckedChange = { viewModel.onTaskCheckedChange(it) },
                onTaskItemRemove = { viewModel.onTaskItemRemove(it); Log.d("long press", "siuuu") })
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskCheckedChange: (Task) -> Unit, onTaskItemRemove: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onTaskItemRemove(task)
                })
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = task.name,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
            )
            Checkbox(checked = task.selected, onCheckedChange = { onTaskCheckedChange(task) })
        }
    }
}