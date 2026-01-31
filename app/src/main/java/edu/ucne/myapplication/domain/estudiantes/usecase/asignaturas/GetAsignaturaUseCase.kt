package edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? = repository.getAsignatura(id)
}