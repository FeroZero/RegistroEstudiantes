package edu.ucne.myapplication.domain.estudiantes.model

data class Estudiante(
    val estudianteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val edad: Int? = null,
)