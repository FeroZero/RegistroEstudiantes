package edu.ucne.myapplication.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf("Estudiantes") }

    fun handleItemClick(destination: Any, item: String) {
        navHostController.navigate(destination) {
            launchSingleTop = true
        }
        selectedItem.value = item
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Registro Académico",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    item {
                        DrawerItem(
                            title = "Estudiantes",
                            icon = Icons.Filled.People,
                            isSelected = selectedItem.value == "Estudiantes",
                            navigateTo = { title ->
                                handleItemClick(Screen.EstudianteList, title)
                            }
                        )

                        DrawerItem(
                            title = "Asignaturas",
                            icon = Icons.Filled.Book,
                            isSelected = selectedItem.value == "Asignaturas",
                            navigateTo = { title ->
                                handleItemClick(Screen.AsignaturaList, title)
                            }
                        )

                        DrawerItem(
                            title = "Tipos de Penalidades",
                            icon = Icons.Filled.Warning,
                            isSelected = selectedItem.value == "Tipos de Penalidades",
                            navigateTo = { title ->
                                handleItemClick(Screen.TipoPenalidadList, title)
                            }
                        )
                    }
                }
            }
        }
    ) {
        content()
    }
}