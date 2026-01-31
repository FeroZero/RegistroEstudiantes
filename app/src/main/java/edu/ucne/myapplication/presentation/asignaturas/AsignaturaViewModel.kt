package edu.ucne.myapplication.presentation.asignaturas

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(AsignaturaUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeAsignaturaUseCase().collect { lista ->
                _state.update { it.copy(asignaturas = lista) }
            }
        }

        val id: Int? = savedStateHandle["id"]
        if (id != null && id > 0) {
            onLoad(id)
        } else {
            onEvent(AsignaturaUiEvent.New)
        }
    }

    private fun validar(): Boolean {
        val codigoRes = validateCodigo(state.value.codigo)
        val nombreRes = validateNombreAsignatura(state.value.nombre)
        val aulaRes = validateAula(state.value.aula)
        val creditosRes = validateCreditos(state.value.creditos)

        _state.update {
            it.copy(
                codigoError = codigoRes.errorMessage,
                nombreError = nombreRes.errorMessage,
                aulaError = aulaRes.errorMessage,
                creditosError = creditosRes.errorMessage
            )
        }

        return codigoRes.isValid && nombreRes.isValid && aulaRes.isValid && creditosRes.isValid
    }

    fun onEvent(event: AsignaturaUiEvent) {
        when (event) {
            is AsignaturaUiEvent.Load -> onLoad(event.id)
            is AsignaturaUiEvent.Delete -> onDelete(event.id)
            is AsignaturaUiEvent.CodigoChanged -> {
                _state.update { it.copy(codigo = event.value, codigoError = null) }
            }
            is AsignaturaUiEvent.NombreChanged -> {
                _state.update { it.copy(nombre = event.value, nombreError = null) }
            }
            is AsignaturaUiEvent.AulaChanged -> {
                _state.update { it.copy(aula = event.value, aulaError = null) }
            }
            is AsignaturaUiEvent.CreditosChanged -> {
                _state.update { it.copy(creditos = event.value, creditosError = null) }
            }
            AsignaturaUiEvent.Save -> onSave()
            AsignaturaUiEvent.New -> {
                _state.update {
                    AsignaturaUiState(
                        asignaturas = _state.value.asignaturas,
                        saved = false,
                        deleted = false
                    )
                }
            }
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) return

        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            asignatura?.let { item ->
                _state.update { it.copy(
                    isNew = false,
                    asignaturaId = item.asignaturaId,
                    codigo = item.codigo,
                    nombre = item.nombre,
                    aula = item.aula,
                    creditos = item.creditos,
                    codigoError = null,
                    nombreError = null,
                    aulaError = null,
                    creditosError = null
                ) }
            }
        }
    }

    private fun onSave() {
        if (!validar()) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val lista = observeAsignaturaUseCase().first()
            val existe = lista.any {
                it.codigo.equals(state.value.codigo, ignoreCase = true) &&
                        it.asignaturaId != state.value.asignaturaId
            }

            if (existe) {
                _state.update { it.copy(
                    codigoError = "Ya existe una asignatura con este código",
                    isSaving = false
                ) }
                return@launch
            }

            val asignatura = Asignatura(
                asignaturaId = if (state.value.asignaturaId == null || state.value.asignaturaId == 0) null else state.value.asignaturaId,
                codigo = state.value.codigo,
                nombre = state.value.nombre,
                aula = state.value.aula,
                creditos = state.value.creditos
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

    fun resetSavedStatus() {
        _state.update {
            it.copy(
                saved = false,
                deleted = false
            )
        }
    }
}