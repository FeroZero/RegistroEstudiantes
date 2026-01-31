package edu.ucne.myapplication.presentation.estudiantes.list

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante

data class EstudianteListUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)