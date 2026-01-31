package edu.ucne.myapplication.presentation.asignaturas.list

sealed class AsignaturaListUiEvent {
    data object Load : AsignaturaListUiEvent()
    data object Refresh : AsignaturaListUiEvent()
    data class Delete(val id: Int) : AsignaturaListUiEvent()
    data class ShowMessage(val message: String) : AsignaturaListUiEvent()
    data object ClearMessage : AsignaturaListUiEvent()
    data object CreateNew : AsignaturaListUiEvent()
    data class Edit(val id: Int) : AsignaturaListUiEvent()
}