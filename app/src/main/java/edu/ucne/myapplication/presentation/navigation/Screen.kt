package edu.ucne.myapplication.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()
    @Serializable
    data class Estudiante(val id: Int) : Screen()
    @Serializable
    data object AsignaturaList : Screen()
    @Serializable
    data class Asignatura(val id: Int) : Screen()
}
