package edu.ucne.myapplication.presentation.asignaturas.list

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura

data class AsignaturaListUiState(
    val isLoading: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)