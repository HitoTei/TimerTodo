package com.example.timertodo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timertodo.ui.edit.EditTaskScreen
import com.example.timertodo.ui.task.TaskScreen
import com.example.timertodo.ui.theme.TimerTodoTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent)
    }
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
                    EditTaskScreen(id = id){
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}


