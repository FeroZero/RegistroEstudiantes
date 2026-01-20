package edu.ucne.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.myapplication.presentation.estudiantes.EstudianteScreen
import edu.ucne.myapplication.presentation.estudiantes.EstudianteUiEvent
import edu.ucne.myapplication.presentation.estudiantes.EstudianteUiViewModel
import edu.ucne.myapplication.presentation.estudiantes.list.EstudianteListScreen
import edu.ucne.myapplication.ui.theme.RegistroEstudianteTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudianteTheme {
                val navController = rememberNavController()
                val viewModel: EstudianteUiViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                NavHost(navController = navController, startDestination = "Lista") {
                    composable("Lista") {
                        EstudianteListScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            onAddEstudiante = {
                                viewModel.onEvent(EstudianteUiEvent.New)
                                navController.navigate("Registro")
                            },
                            onEditEstudiante = { id ->
                                viewModel.onEvent(EstudianteUiEvent.Edit(id))
                                navController.navigate("Registro")
                            }
                        )
                    }
                    composable("Registro") {
                        EstudianteScreen(
                            viewModel = viewModel,
                            onNavigateBack = {
                                viewModel.resetSavedStatus()
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistroEstudianteTheme {
        Greeting("Android")
    }
}