package edu.ucne.myapplication.presentation.estudiantes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.DeleteEstudianteUseCase
import edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes.ObserveEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstudianteListViewModel @Inject constructor(
    private val observeEstudianteUseCase: ObserveEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EstudianteListUiState(isLoading = true))
    val state: StateFlow<EstudianteListUiState> = _state.asStateFlow()

    init {
        loadEstudiantes()
    }

    fun onEvent(event: EstudianteListUiEvent) {
        when (event) {
            EstudianteListUiEvent.Load -> loadEstudiantes()
            EstudianteListUiEvent.Refresh -> loadEstudiantes()
            is EstudianteListUiEvent.Delete -> onDelete(event.id)
            is EstudianteListUiEvent.ShowMessage -> _state.update {
                it.copy(message = event.message)
            }
            EstudianteListUiEvent.ClearMessage -> _state.update {
                it.copy(message = null)
            }
            EstudianteListUiEvent.CreateNew -> _state.update {
                it.copy(navigateToCreate = true)
            }
            is EstudianteListUiEvent.Edit -> _state.update {
                it.copy(navigateToEditId = event.id)
            }
        }
    }

    fun loadEstudiantes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeEstudianteUseCase().collectLatest { list ->
                _state.update {
                    it.copy(isLoading = false, estudiantes = list, message = null)
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEstudianteUseCase(id)
            onEvent(EstudianteListUiEvent.ShowMessage("Estudiante eliminado con éxito"))
        }
    }
}