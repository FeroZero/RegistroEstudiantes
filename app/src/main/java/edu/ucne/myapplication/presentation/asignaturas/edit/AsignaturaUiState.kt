package edu.ucne.myapplication.presentation.asignaturas.edit

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura

data class AsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: String = "",

    val codigoError: String? = null,
    val nombreError: String? = null,
    val aulaError: String? = null,
    val creditosError: String? = null,

    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList()
)