package edu.ucne.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.myapplication.presentation.estudiantes.EstudianteScreen
import edu.ucne.myapplication.presentation.estudiantes.EstudianteUiViewModel
import edu.ucne.myapplication.presentation.estudiantes.list.EstudianteListScreen

@Composable
fun myapplicacionNavHost(
    navHostController: NavHostController
) {
    val viewModel: EstudianteUiViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()
    NavHost(
        navController = navHostController,
        startDestination = Screen.EstudianteList
    ) {
        composable<Screen.EstudianteList>{
            EstudianteListScreen(
                onAddEstudiante = {
                    navHostController.navigate(Screen.Estudiante(0))
                },
                onEditEstudiante = { id ->
                    navHostController.navigate(Screen.Estudiante(id))
                },
                onEvent = { event ->
                    viewModel.onEvent(event)
                },
                state = state
            )
        }

        composable<Screen.Estudiante> {
            EstudianteScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navHostController.navigateUp()
                },
            )
        }
    }
}