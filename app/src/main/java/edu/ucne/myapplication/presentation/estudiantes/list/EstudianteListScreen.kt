package edu.ucne.myapplication.presentation.estudiantes.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.presentation.estudiantes.EstudianteUiEvent
import edu.ucne.myapplication.presentation.estudiantes.EstudianteUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteListScreen(
    state: EstudianteUiState,
    onEvent: (EstudianteUiEvent) -> Unit,
    onEditEstudiante: (Int) -> Unit,
    onAddEstudiante: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Estudiantes", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEstudiante,
                containerColor = Color(0xFFD0BCFF)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Estudiante")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            items(state.estudiantes) { estudiante ->
                EstudianteItem(
                    estudiante = estudiante,
                    onEdit = {
                        onEditEstudiante(estudiante.estudianteId ?: 0)
                    },
                    onDelete = {
                        onEvent(EstudianteUiEvent.Load(estudiante.estudianteId))
                        onEvent(EstudianteUiEvent.Delete(estudiante.estudianteId ?: 0))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EstudianteItem(
    estudiante: Estudiante,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFF3EDF7)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = estudiante.nombres,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "Edad: ${estudiante.edad}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = estudiante.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF6750A4)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFB3261E)
                    )
                }
            }
        }
    }
}