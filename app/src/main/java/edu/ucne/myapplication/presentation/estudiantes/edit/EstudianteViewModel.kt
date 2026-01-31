package edu.ucne.myapplication.presentation.estudiantes.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.DeleteEstudianteUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.GetEstudianteUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.ObserveEstudianteUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.UpsertEstudianteUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.validateEdad
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.validateEmail
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.validateNombres
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    private val observeEstudianteUseCase: ObserveEstudianteUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(EstudianteUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeEstudianteUseCase().collect { lista ->
                _state.update { it.copy(estudiantes = lista) }
            }
        }
        val id: Int? = savedStateHandle["id"]
        if (id != null && id > 0) {
            onLoad(id)
        } else {
            onEvent(EstudianteUiEvent.New)
        }
    }
    private fun validar(): Boolean {
        val currentNombres = state.value.nombres
        val currentEmail = state.value.email
        val currentEdad = state.value.edad
        val nombresRes = validateNombres(currentNombres)
        val emailRes = validateEmail(currentEmail)
        val edadRes = validateEdad(currentEdad)

        _state.update {
            it.copy(
                nombresError = nombresRes.errorMessage,
                emailError = emailRes.errorMessage,
                edadError = edadRes.errorMessage
            )
        }

        return nombresRes.isValid && emailRes.isValid && edadRes.isValid
    }
    fun onEvent(event: EstudianteUiEvent) {
        when (event) {
            is EstudianteUiEvent.Load -> onLoad(event.id)
            is EstudianteUiEvent.Edit -> onLoad(event.id)
            is EstudianteUiEvent.Delete -> onDelete(event.id)
            is EstudianteUiEvent.nombresChanged -> {
                _state.update { it.copy(nombres = event.value, nombresError = null) }
            }
            is EstudianteUiEvent.emailChanged -> {
                _state.update { it.copy(email = event.value, emailError = null) }
            }
            is EstudianteUiEvent.edadChanged -> {
                _state.update { it.copy(edad = event.value, edadError = null) }
            }
            EstudianteUiEvent.Save -> onSave()
            EstudianteUiEvent.New -> {
                _state.update {
                    EstudianteUiState(
                        estudiantes = _state.value.estudiantes,
                        saved = false,
                        deleted = false
                    )
                }
            }
        }
    }

    private fun onLoad(id: Int?) {
        println("Cargando estudiante con ID: $id")
        if (id == null || id == 0) return

        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            println("Estudiante encontrado: ${estudiante?.nombres}")
            estudiante?.let { item ->
                _state.update { it.copy(
                    isNew = false,
                    estudianteId = item.estudianteId,
                    nombres = item.nombres,
                    email = item.email,
                    edad = item.edad,
                    nombresError = null,
                    emailError = null,
                    edadError = null
                ) }
            }
        }
    }

    private fun onSave() {
        if (!validar()) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val listaEstudiantes = observeEstudianteUseCase().first()
            val existe = listaEstudiantes.any {
                it.nombres.equals(state.value.nombres, ignoreCase = true) &&
                        it.estudianteId != state.value.estudianteId
            }

            if (existe) {
                _state.update { it.copy(
                    nombresError = "Ya existe un estudiante con este nombre",
                    isSaving = false
                ) }
                return@launch
            }

            val estudiante = Estudiante(
                estudianteId = if (state.value.estudianteId == null || state.value.estudianteId == 0) null else state.value.estudianteId,
                nombres = state.value.nombres,
                email = state.value.email,
                edad = state.value.edad ?: 0
            )

            upsertEstudianteUseCase(estudiante)
            _state.update { it.copy(isSaving = false, saved = true) }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEstudianteUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
    fun resetSavedStatus() {
        _state.update {
            it.copy(
                saved = false,
                deleted = false
            )
        }
    }
}