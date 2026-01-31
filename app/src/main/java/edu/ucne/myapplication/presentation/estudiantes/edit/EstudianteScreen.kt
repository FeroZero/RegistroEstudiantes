package edu.ucne.myapplication.presentation.estudiantes.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EstudianteScreen(
    viewModel: EstudianteViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onNavigateBack()
        }
    }

    EstudianteBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EstudianteBody(
    state: EstudianteUiState,
    onEvent: (EstudianteUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Estudiantes") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        label = { Text("Nombres") },
                        value = state.nombres,
                        onValueChange = { onEvent(EstudianteUiEvent.nombresChanged(it)) },
                        isError = state.nombresError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombresError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Email") },
                        value = state.email,
                        onValueChange = { onEvent(EstudianteUiEvent.emailChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = state.emailError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.emailError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Edad") },
                        value = state.edad?.toString() ?: "",
                        onValueChange = {
                            val valor = it.toIntOrNull()
                            onEvent(EstudianteUiEvent.edadChanged(valor))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.edadError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.edadError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = { onEvent(EstudianteUiEvent.New) }) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }

                        Button(
                            onClick = { onEvent(EstudianteUiEvent.Save) },
                            enabled = !state.isSaving
                        ) {
                            Icon(Icons.Default.Save, contentDescription = "Guardar")
                            Text(if (state.isSaving) "Guardando..." else "Guardar")
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun EstudianteBodyPreview() {
    val state = EstudianteUiState(
        estudianteId = 1,
        nombres = "Juan Perez",
        email = "juan@gmail.com",
        edad = 20,
        isNew = false
    )
    EstudianteBody(
        state = state,
        onEvent = {}
    )
}