package edu.ucne.myapplication.domain.estudiantes.usecase

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository

class DeleteEstudianteUseCase(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}