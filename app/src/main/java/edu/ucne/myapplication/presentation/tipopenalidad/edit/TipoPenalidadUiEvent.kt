package edu.ucne.myapplication.presentation.tipopenalidad.edit

sealed interface TipoPenalidadUiEvent {
    data class Load(val id: Int?) : TipoPenalidadUiEvent
    data class NombreChanged(val value: String) : TipoPenalidadUiEvent
    data class DescripcionChanged(val value: String) : TipoPenalidadUiEvent
    data class PuntosChanged(val value: Int?) : TipoPenalidadUiEvent
    data object Save : TipoPenalidadUiEvent
    data class Delete(val id: Int) : TipoPenalidadUiEvent
    data object New : TipoPenalidadUiEvent
    data class Edit(val id: Int) : TipoPenalidadUiEvent
}