package edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas

import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val asignaturaRepository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) {
        asignaturaRepository.delete(id)
    }
}