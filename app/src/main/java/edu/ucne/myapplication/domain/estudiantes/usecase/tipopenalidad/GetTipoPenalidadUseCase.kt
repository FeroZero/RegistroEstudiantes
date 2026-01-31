package edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import edu.ucne.myapplication.domain.estudiantes.repository.TipoPenalidadRepository
import javax.inject.Inject

class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? = repository.getTipoPenalidad(id)
}