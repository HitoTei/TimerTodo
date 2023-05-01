package com.example.timertodo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timertodo.ui.edit.EditTaskScreen
import com.example.timertodo.ui.task.TaskScreen
import com.example.timertodo.ui.theme.TimerTodoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timertodo.data.TaskRepository
import com.example.timertodo.data.TaskRepositoryImpl

class MainActivity : ComponentActivity() {
    private val dao = RoomApplication.database.taskDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp(TaskRepositoryImpl(dao))
        }
    }
}

@Composable
fun MainApp(
    repository: TaskRepository,
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    TimerTodoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = "task") {
                composable(route = "task") {
                    TaskScreen{ task ->
                        navController.navigate("edit/${task.id}")
                    }
                }
                composable(route = "edit/{id}",
                    arguments = listOf(
                        navArgument("id") { type = NavType.IntType }
                    )) { navBackStackEntry ->
                    val id = navBackStackEntry.arguments?.getInt("id") ?: 0
                    EditTaskScreen(id)
                }
            }
        }
    }
}


