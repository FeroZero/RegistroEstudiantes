package edu.ucne.myapplication.presentation.asignaturas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.GetAsignaturaUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.ObserveAsignaturaUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.UpsertAsignaturaUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.validateAula
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.validateCodigo
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.validateCreditos
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.validateNombreAsignatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AsignaturaUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeAsignaturaUseCase().collect { lista ->
                _state.update { it.copy(asignaturas = lista) }
            }
        }
    }

    fun onEvent(event: AsignaturaUiEvent) {
        when (event) {
            is AsignaturaUiEvent.Load -> onLoad(event.id)
            is AsignaturaUiEvent.CodigoChanged -> _state.update { it.copy(codigo = event.value, codigoError = null) }
            is AsignaturaUiEvent.NombreChanged -> _state.update { it.copy(nombre = event.value, nombreError = null) }
            is AsignaturaUiEvent.AulaChanged -> _state.update { it.copy(aula = event.value, aulaError = null) }
            is AsignaturaUiEvent.CreditosChanged -> _state.update { it.copy(creditos = event.value, creditosError = null) }
            AsignaturaUiEvent.Save -> onSave()
            is AsignaturaUiEvent.Delete -> onDelete(event.id)
            AsignaturaUiEvent.New -> _state.update { AsignaturaUiState(asignaturas = _state.value.asignaturas) }
        }
    }

    private fun onLoad(id: Int) {
        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            asignatura?.let {
                _state.update { it.copy(
                    isNew = false,
                    asignaturaId = it.asignaturaId,
                    codigo = it.codigo,
                    nombre = it.nombre,
                    aula = it.aula,
                    creditos = it.creditos
                ) }
            }
        }
    }

    private fun onSave() {
        if (!validar()) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val asignatura = Asignatura(
                AsignaturaId = if (state.value.asignaturaId == 0) null else state.value.asignaturaId,
                Codigo = state.value.codigo,
                Nombre = state.value.nombre,
                Aula = state.value.aula,
                Creditos = state.value.creditos
            )
            upsertAsignaturaUseCase(asignatura)
            _state.update { it.copy(isSaving = false, saved = true) }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteAsignaturaUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }

    private fun validar(): Boolean {
        val c = validateCodigo(state.value.codigo)
        val n = validateNombreAsignatura(state.value.nombre)
        val a = validateAula(state.value.aula)
        val cr = validateCreditos(state.value.creditos)

        _state.update { it.copy(
            codigoError = c.errorMessage,
            nombreError = n.errorMessage,
            aulaError = a.errorMessage,
            creditosError = cr.errorMessage
        ) }
        return c.isValid && n.isValid && a.isValid && cr.isValid
    }

    fun resetSavedStatus() = _state.update { it.copy(saved = false, deleted = false) }
}