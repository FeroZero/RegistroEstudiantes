package edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura): Result<Int> {
        val codigoResult = validateCodigo(asignatura.Codigo)
        if (!codigoResult.isValid) {
            return Result.failure(IllegalArgumentException(codigoResult.errorMessage))
        }

        val nombreResult = validateNombreAsignatura(asignatura.Nombre)
        if (!nombreResult.isValid) {
            return Result.failure(IllegalArgumentException(nombreResult.errorMessage))
        }

        val aulaResult = validateAula(asignatura.Aula)
        if (!aulaResult.isValid) {
            return Result.failure(IllegalArgumentException(aulaResult.errorMessage))
        }

        val creditosResult = validateCreditos(asignatura.Creditos)
        if (!creditosResult.isValid) {
            return Result.failure(IllegalArgumentException(creditosResult.errorMessage))
        }

        return runCatching { repository.upsert(asignatura) }
    }
}