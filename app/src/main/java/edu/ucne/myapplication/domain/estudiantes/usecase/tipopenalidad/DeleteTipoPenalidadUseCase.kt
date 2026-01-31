package edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad

import edu.ucne.myapplication.domain.estudiantes.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}