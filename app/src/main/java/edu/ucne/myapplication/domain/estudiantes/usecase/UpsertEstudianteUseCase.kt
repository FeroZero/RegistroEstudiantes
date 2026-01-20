package edu.ucne.myapplication.domain.estudiantes.usecase

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante): Result<Int> {
        val nombresResult = validateNombres(estudiante.nombres)
        if (!nombresResult.isValid) {
            return Result.failure(IllegalArgumentException(nombresResult.errorMessage))
        }
        val emailResult = validateEmail(estudiante.email)
        if (!emailResult.isValid) {
            return Result.failure(IllegalArgumentException(nombresResult.errorMessage))
        }
        val edadResult = validateEdad(estudiante.edad)
        if (!edadResult.isValid) {
            return Result.failure(IllegalArgumentException(nombresResult.errorMessage))
        }
        return runCatching { repository.upsert(estudiante) }
    }
}