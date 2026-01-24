package edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.observeEstudiante()
}