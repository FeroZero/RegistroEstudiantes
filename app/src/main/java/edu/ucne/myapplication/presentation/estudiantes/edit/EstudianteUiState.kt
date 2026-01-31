package edu.ucne.myapplication.presentation.estudiantes.edit

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante

data class EstudianteUiState(

    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: Int? = null,
    val estudiantes: List<Estudiante> = emptyList(),
    val nombresError: String? = null,
    val emailError: String? = null,
    val edadError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)