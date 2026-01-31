package edu.ucne.myapplication.presentation.tipopenalidad.list

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad

data class TipoPenalidadListUiState(
    val isLoading: Boolean = false,
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)