package edu.ucne.myapplication.domain.estudiantes.usecase

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow

class ObserveEstudianteUseCase(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.observeEstudiante()
}