package edu.ucne.myapplication.presentation.asignaturas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas.ObserveAsignaturaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaListViewModel @Inject constructor(
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AsignaturaListUiState(isLoading = true))
    val state: StateFlow<AsignaturaListUiState> = _state.asStateFlow()

    init {
        loadAsignaturas()
    }

    fun onEvent(event: AsignaturaListUiEvent) {
        when (event) {
            AsignaturaListUiEvent.Load -> loadAsignaturas()
            AsignaturaListUiEvent.Refresh -> loadAsignaturas()
            is AsignaturaListUiEvent.Delete -> onDelete(event.id)
            is AsignaturaListUiEvent.ShowMessage -> _state.update {
                it.copy(message = event.message)
            }
            AsignaturaListUiEvent.ClearMessage -> _state.update {
                it.copy(message = null)
            }
            AsignaturaListUiEvent.CreateNew -> _state.update {
                it.copy(navigateToCreate = true)
            }
            is AsignaturaListUiEvent.Edit -> _state.update {
                it.copy(navigateToEditId = event.id)
            }
        }
    }

    private fun loadAsignaturas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeAsignaturaUseCase().collectLatest { list ->
                _state.update {
                    it.copy(isLoading = false, asignaturas = list, message = null)
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(id)
            onEvent(AsignaturaListUiEvent.ShowMessage("Asignatura eliminada con éxito"))
        }
    }

    fun onNavigatedToCreate() {
        _state.update { it.copy(navigateToCreate = false) }
    }

    fun onNavigatedToEdit() {
        _state.update { it.copy(navigateToEditId = null) }
    }
}