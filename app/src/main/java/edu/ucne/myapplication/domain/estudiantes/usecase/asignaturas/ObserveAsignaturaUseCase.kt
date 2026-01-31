package edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> = repository.observeAsignatura()
}