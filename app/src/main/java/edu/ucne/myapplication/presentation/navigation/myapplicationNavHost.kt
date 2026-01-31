package edu.ucne.myapplication.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.myapplication.presentation.asignaturas.edit.AsignaturaScreen
import edu.ucne.myapplication.presentation.asignaturas.edit.AsignaturaViewModel
import edu.ucne.myapplication.presentation.asignaturas.list.AsignaturaListScreen
import edu.ucne.myapplication.presentation.asignaturas.list.AsignaturaListViewModel
import edu.ucne.myapplication.presentation.estudiantes.edit.EstudianteScreen
import edu.ucne.myapplication.presentation.estudiantes.edit.EstudianteViewModel
import edu.ucne.myapplication.presentation.estudiantes.list.EstudianteListViewModel
import edu.ucne.myapplication.presentation.estudiantes.list.EstudianteListScreen
import edu.ucne.myapplication.presentation.tipopenalidad.edit.TipoPenalidadScreen
import edu.ucne.myapplication.presentation.tipopenalidad.edit.TipoPenalidadViewModel
import edu.ucne.myapplication.presentation.tipopenalidad.list.TipoPenalidadListScreen
import edu.ucne.myapplication.presentation.tipopenalidad.list.TipoPenalidadListViewModel
import kotlinx.coroutines.launch

@Composable
fun myapplicacionNavHost(
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val EstudiantelistViewModel: EstudianteListViewModel = hiltViewModel()
    val AsignaturalistViewModel: AsignaturaListViewModel = hiltViewModel()
    val TipoPenalidadlistViewModel: TipoPenalidadListViewModel = hiltViewModel()
    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    viewModel = EstudiantelistViewModel,
                    onNavigateToCreate = {
                        navHostController.navigate(Screen.Estudiante(0))
                    },
                    onDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onNavigateToEdit = { id ->
                        navHostController.navigate(Screen.Estudiante(id))
                    }
                )
            }

            composable<Screen.Estudiante> {
                val viewModel: EstudianteViewModel = hiltViewModel()


                EstudianteScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        viewModel.resetSavedStatus()
                        navHostController.navigateUp()
                    },
                )
            }

            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    viewModel = AsignaturalistViewModel,
                    onNavigateToCreate = {
                        navHostController.navigate(Screen.Asignatura(0))
                    },
                    onDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onNavigateToEdit = { id ->
                        navHostController.navigate(Screen.Asignatura(id))
                    }
                )
            }
            composable<Screen.Asignatura> {
                val viewModel: AsignaturaViewModel = hiltViewModel()

                AsignaturaScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        viewModel.resetSavedStatus()
                        navHostController.navigateUp()
                    },
                )
            }
            composable<Screen.TipoPenalidadList> {
                TipoPenalidadListScreen(
                    viewModel = TipoPenalidadlistViewModel,
                    onNavigateToCreate = {
                        navHostController.navigate(Screen.TipoPenalidad(0))
                    },
                    onDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onNavigateToEdit = { id ->
                        navHostController.navigate(Screen.TipoPenalidad(id))
                    }
                )
            }
            composable<Screen.TipoPenalidad> {
                val viewModel: TipoPenalidadViewModel = hiltViewModel()

                TipoPenalidadScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        viewModel.resetSavedStatus()
                        navHostController.navigateUp()
                    },
                )
            }
        }
    }
}