package edu.ucne.myapplication.presentation.estudiantes.edit

class EditTaskUiState(
    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: Int? = null,
    val nombresError: String? = null,
    val emailError: String? = null,
    val edadError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
