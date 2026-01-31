package edu.ucne.myapplication.presentation.tipopenalidad.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.DeleteTipoPenalidadUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.GetTipoPenalidadUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.ObserveTipoPenalidadUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.UpsertTipoPenalidadUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.validateDescripcion
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.validateNombrePenalidad
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.validatePuntosDescuento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.equals

@HiltViewModel
class TipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    private val observeTipoPenalidadUseCase: ObserveTipoPenalidadUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(TipoPenalidadUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeTipoPenalidadUseCase().collect { lista ->
                _state.update { it.copy(tiposPenalidades = lista) }
            }
        }
        val id: Int? = savedStateHandle["id"]
        if (id != null && id > 0) {
            onLoad(id)
        } else {
            onEvent(TipoPenalidadUiEvent.New)
        }
    }

    private fun validar(): Boolean {
        val currentNombre = state.value.nombre
        val currentDescripcion = state.value.descripcion
        val currentPuntos = state.value.puntosDescuento

        val nombreRes = validateNombrePenalidad(currentNombre)
        val descripcionRes = validateDescripcion(currentDescripcion)
        val puntosRes = validatePuntosDescuento(currentPuntos)

        _state.update {
            it.copy(
                nombreError = nombreRes.errorMessage,
                descripcionError = descripcionRes.errorMessage,
                puntosError = puntosRes.errorMessage
            )
        }

        return nombreRes.isValid && descripcionRes.isValid && puntosRes.isValid
    }

    fun onEvent(event: TipoPenalidadUiEvent) {
        when (event) {
            is TipoPenalidadUiEvent.Load -> onLoad(event.id)
            is TipoPenalidadUiEvent.Edit -> onLoad(event.id)
            is TipoPenalidadUiEvent.Delete -> onDelete(event.id)
            is TipoPenalidadUiEvent.NombreChanged -> {
                _state.update { it.copy(nombre = event.value, nombreError = null) }
            }
            is TipoPenalidadUiEvent.DescripcionChanged -> {
                _state.update { it.copy(descripcion = event.value, descripcionError = null) }
            }
            is TipoPenalidadUiEvent.PuntosChanged -> {
                _state.update { it.copy(puntosDescuento = event.value, puntosError = null) }
            }
            TipoPenalidadUiEvent.Save -> onSave()
            TipoPenalidadUiEvent.New -> {
                _state.update {
                    TipoPenalidadUiState(
                        tiposPenalidades = _state.value.tiposPenalidades,
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
            val penalidad = getTipoPenalidadUseCase(id)
            penalidad?.let { item ->
                _state.update { it.copy(
                    tipoId = item.tipoId,
                    nombre = item.nombre,
                    descripcion = item.descripcion,
                    puntosDescuento = item.puntosDescuento,
                    nombreError = null,
                    descripcionError = null,
                    puntosError = null
                ) }
            }
        }
    }

    private fun onSave() {
        if (!validar()) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            // Validación de Unicidad por Nombre
            val listaActual = observeTipoPenalidadUseCase().first()
            val existe = listaActual.any {
                it.nombre.equals(state.value.nombre, ignoreCase = true) &&
                        it.tipoId != state.value.tipoId
            }

            if (existe) {
                _state.update { it.copy(
                    nombreError = "Ya existe un tipo de penalidad registrado con este nombre",
                    isSaving = false
                ) }
                return@launch
            }

            val penalidad = TipoPenalidad(
                tipoId = if (state.value.tipoId == null || state.value.tipoId == 0) null else state.value.tipoId,
                nombre = state.value.nombre,
                descripcion = state.value.descripcion,
                puntosDescuento = state.value.puntosDescuento ?: 0
            )

            upsertTipoPenalidadUseCase(penalidad)
            _state.update { it.copy(isSaving = false, saved = true) }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteTipoPenalidadUseCase(id)
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