package edu.ucne.myapplication.presentation.asignaturas.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.myapplication.domain.estudiantes.model.Asignatura

@Composable
fun AsignaturaListScreen(
    viewModel: AsignaturaListViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {},
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AsignaturaListBody(
        state = state,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer,
        onNavigateToCreate = onNavigateToCreate,
        onNavigateToEdit = onNavigateToEdit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsignaturaListBody(
    state: AsignaturaListUiState,
    onEvent: (AsignaturaListUiEvent) -> Unit,
    onDrawer: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Asignaturas", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate,
                containerColor = Color(0xFFD0BCFF)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Asignatura")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.asignaturas.isEmpty()) {
                Text(
                    text = "No hay asignaturas registradas",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.asignaturas) { asignatura ->
                        AsignaturaItem(
                            asignatura = asignatura,
                            onEdit = {
                                onNavigateToEdit(asignatura.asignaturaId ?: 0)
                            },
                            onDelete = {
                                onEvent(AsignaturaListUiEvent.Delete(asignatura.asignaturaId ?: 0))
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AsignaturaItem(
    asignatura: Asignatura,
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
                    text = asignatura.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "Código: ${asignatura.codigo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Aula: ${asignatura.aula}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = "Créditos: ${asignatura.creditos}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar", tint = Color(0xFF6750A4))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = Color(0xFFB3261E))
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun AsignaturaListBodyPreview() {
    val state = AsignaturaListUiState(
        asignaturas = listOf(
            Asignatura(asignaturaId = 1, codigo = "MAT-101", nombre = "Matemáticas I", aula = "A-21", creditos = "4"),
            Asignatura(asignaturaId = 2, codigo = "PRO-202", nombre = "Programación II", aula = "L-05", creditos = "3")
        )
    )
    AsignaturaListBody(
        state = state,
        onEvent = {},
        onDrawer = {},
        onNavigateToCreate = {},
        onNavigateToEdit = {}
    )
}