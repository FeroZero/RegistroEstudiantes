package edu.ucne.myapplication.presentation.estudiantes.edit

sealed interface EditEstudianteUiEvent {
    data class Load(val id: Int?) : EditEstudianteUiEvent
    data class nombresChanged(val value: String) : EditEstudianteUiEvent

    data class emailChanged(val value: String) : EditEstudianteUiEvent

    data class edadChanged(val value: Int?) : EditEstudianteUiEvent
    data object Save : EditEstudianteUiEvent
    data object Delete : EditEstudianteUiEvent
}