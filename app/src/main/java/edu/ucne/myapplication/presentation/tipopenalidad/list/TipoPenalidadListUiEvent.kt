package edu.ucne.myapplication.presentation.tipopenalidad.list

sealed class TipoPenalidadListUiEvent {
    data object Load : TipoPenalidadListUiEvent()
    data object Refresh : TipoPenalidadListUiEvent()
    data class Delete(val id: Int) : TipoPenalidadListUiEvent()
    data class ShowMessage(val message: String) : TipoPenalidadListUiEvent()
    data object ClearMessage : TipoPenalidadListUiEvent()
    data object CreateNew : TipoPenalidadListUiEvent()
    data class Edit(val id: Int) : TipoPenalidadListUiEvent()
}