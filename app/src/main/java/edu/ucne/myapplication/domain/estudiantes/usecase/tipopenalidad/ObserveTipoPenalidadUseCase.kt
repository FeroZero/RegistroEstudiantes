package edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import edu.ucne.myapplication.domain.estudiantes.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> = repository.observeTiposPenalidades()
}