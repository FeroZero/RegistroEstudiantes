package edu.ucne.myapplication.domain.estudiantes.usecase

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}