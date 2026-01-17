package edu.ucne.myapplication.domain.estudiantes.usecase

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository

class GetEstudianteUseCase(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? = repository.getEstudiante(id)
}