package edu.ucne.myapplication.domain.estudiantes.model

data class TipoPenalidad (
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int = 0
)

