package edu.ucne.myapplication.presentation.tipopenalidad.list

import androidx.activity.result.launch
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.DeleteTipoPenalidadUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad.ObserveTipoPenalidadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadListViewModel @Inject constructor(
    private val observeTipoPenalidadUseCase: ObserveTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TipoPenalidadListUiState(isLoading = true))
    val state: StateFlow<TipoPenalidadListUiState> = _state.asStateFlow()

    init {
        loadTiposPenalidades()
    }

    fun onEvent(event: TipoPenalidadListUiEvent) {
        when (event) {
            TipoPenalidadListUiEvent.Load -> loadTiposPenalidades()
            TipoPenalidadListUiEvent.Refresh -> loadTiposPenalidades()
            is TipoPenalidadListUiEvent.Delete -> onDelete(event.id)
            is TipoPenalidadListUiEvent.ShowMessage -> _state.update {
                it.copy(message = event.message)
            }
            TipoPenalidadListUiEvent.ClearMessage -> _state.update {
                it.copy(message = null)
            }
            TipoPenalidadListUiEvent.CreateNew -> _state.update {
                it.copy(navigateToCreate = true)
            }
            is TipoPenalidadListUiEvent.Edit -> _state.update {
                it.copy(navigateToEditId = event.id)
            }
        }
    }

    fun loadTiposPenalidades() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeTipoPenalidadUseCase().collectLatest { list ->
                _state.update {
                    it.copy(isLoading = false, tiposPenalidades = list, message = null)
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteTipoPenalidadUseCase(id)
            onEvent(TipoPenalidadListUiEvent.ShowMessage("Tipo de penalidad eliminado con éxito"))
        }
    }

    fun onNavigatedToCreate() {
        _state.update { it.copy(navigateToCreate = false) }
    }

    fun onNavigatedToEdit() {
        _state.update { it.copy(navigateToEditId = null) }
    }
}