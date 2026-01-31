package edu.ucne.myapplication.domain.estudiantes.model

data class Asignatura(
    val asignaturaId: Int? = null,
    val codigo: String,
    val nombre: String,
    val aula: String,
    val creditos: String,
)