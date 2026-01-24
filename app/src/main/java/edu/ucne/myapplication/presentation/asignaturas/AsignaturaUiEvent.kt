package edu.ucne.myapplication.presentation.asignaturas

sealed interface AsignaturaUiEvent {
    data class Load(val id: Int) : AsignaturaUiEvent
    data class CodigoChanged(val value: String) : AsignaturaUiEvent class NombreChanged(val value: String) : AsignaturaUiEvent
    data class AulaChanged(val value: String) : AsignaturaUiEvent
    data class CreditosChanged(val value: String) : AsignaturaUiEvent
    data object Save : AsignaturaUiEvent
    data class Delete(val id: Int) : AsignaturaUiEvent
    data object New : AsignaturaUiEvent
}