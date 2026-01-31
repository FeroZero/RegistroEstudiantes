package edu.ucne.myapplication.presentation.estudiantes.list

sealed class EstudianteListUiEvent {
    data object Load : EstudianteListUiEvent()
    data object Refresh : EstudianteListUiEvent()
    data class Delete(val id: Int) : EstudianteListUiEvent()
    data class ShowMessage(val message: String) : EstudianteListUiEvent()
    data object ClearMessage : EstudianteListUiEvent()
    data object CreateNew : EstudianteListUiEvent()
    data class Edit(val id: Int) : EstudianteListUiEvent()
}