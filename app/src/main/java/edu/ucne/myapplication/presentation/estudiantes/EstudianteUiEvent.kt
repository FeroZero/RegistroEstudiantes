package edu.ucne.myapplication.presentation.estudiantes

sealed interface EstudianteUiEvent {
    data class Load(val id: Int?) : EstudianteUiEvent
    data class nombresChanged(val value: String) : EstudianteUiEvent
    data class emailChanged(val value: String) : EstudianteUiEvent

    data class edadChanged(val value: Int?) : EstudianteUiEvent
    data object Save : EstudianteUiEvent
    data object Delete : EstudianteUiEvent
}