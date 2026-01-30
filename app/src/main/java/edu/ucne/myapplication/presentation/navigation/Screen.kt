package edu.ucne.myapplication.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()
    @Serializable
    data class Estudiante(val id: Int) : Screen()

}