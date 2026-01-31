package edu.ucne.myapplication.presentation.asignaturas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun AsignaturaScreen(
    viewModel: AsignaturaViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onNavigateBack()
        }
    }

    AsignaturaBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsignaturaBody(
    state: AsignaturaUiState,
    onEvent: (AsignaturaUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Asignaturas") })
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
                        label = { Text("Código") },
                        value = state.codigo,
                        onValueChange = { onEvent(AsignaturaUiEvent.CodigoChanged(it)) },
                        isError = state.codigoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.codigoError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Nombre") },
                        value = state.nombre,
                        onValueChange = { onEvent(AsignaturaUiEvent.NombreChanged(it)) },
                        isError = state.nombreError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombreError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Aula") },
                        value = state.aula,
                        onValueChange = { onEvent(AsignaturaUiEvent.AulaChanged(it)) },
                        isError = state.aulaError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.aulaError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Créditos") },
                        value = state.creditos,
                        onValueChange = { onEvent(AsignaturaUiEvent.CreditosChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.creditosError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.creditosError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = { onEvent(AsignaturaUiEvent.New) }) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }

                        Button(
                            onClick = { onEvent(AsignaturaUiEvent.Save) },
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
private fun AsignaturaBodyPreview() {
    val state = AsignaturaUiState(
        asignaturaId = 1,
        codigo = "AP2-101",
        nombre = "Aplicada II",
        aula = "Virtual",
        creditos = "4",
        isNew = false
    )
    AsignaturaBody(
        state = state,
        onEvent = {}
    )
}