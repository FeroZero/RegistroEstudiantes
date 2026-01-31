package edu.ucne.myapplication.presentation.tipopenalidad.edit

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad

data class TipoPenalidadUiState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int? = null,

    val tiposPenalidades: List<TipoPenalidad> = emptyList(),

    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosError: String? = null,

    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)