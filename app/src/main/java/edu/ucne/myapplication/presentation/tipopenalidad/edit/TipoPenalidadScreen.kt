package edu.ucne.myapplication.presentation.tipopenalidad.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TipoPenalidadScreen(
    viewModel: TipoPenalidadViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onNavigateBack()
        }
    }

    TipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TipoPenalidadBody(
    state: TipoPenalidadUiState,
    onEvent: (TipoPenalidadUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Tipos de Penalidades") })
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
                    // Campo Nombre
                    OutlinedTextField(
                        label = { Text("Nombre") },
                        value = state.nombre,
                        onValueChange = { onEvent(TipoPenalidadUiEvent.NombreChanged(it)) },
                        isError = state.nombreError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombreError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Descripción
                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = state.descripcion,
                        onValueChange = { onEvent(TipoPenalidadUiEvent.DescripcionChanged(it)) },
                        isError = state.descripcionError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.descripcionError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Puntos Descuento
                    OutlinedTextField(
                        label = { Text("Puntos Descuento") },
                        value = state.puntosDescuento?.toString() ?: "",
                        onValueChange = {
                            val valor = it.toIntOrNull()
                            onEvent(TipoPenalidadUiEvent.PuntosChanged(valor))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.puntosError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.puntosError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = { onEvent(TipoPenalidadUiEvent.New) }) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }

                        Button(
                            onClick = { onEvent(TipoPenalidadUiEvent.Save) },
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
private fun TipoPenalidadBodyPreview() {
    val state = TipoPenalidadUiState(
        tipoId = 1,
        nombre = "Llegada Tardía",
        descripcion = "El estudiante llegó después de la hora permitida",
        puntosDescuento = 10,
        isNew = false
    )
    TipoPenalidadBody(
        state = state,
        onEvent = {}
    )
}