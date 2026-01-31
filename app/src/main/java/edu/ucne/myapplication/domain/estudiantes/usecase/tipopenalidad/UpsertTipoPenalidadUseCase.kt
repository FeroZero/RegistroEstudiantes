package edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import edu.ucne.myapplication.domain.estudiantes.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Int> {
        // 1. Validar Nombre (Obligatorio)
        val nombreResult = validateNombrePenalidad(tipoPenalidad.nombre)
        if (!nombreResult.isValid) {
            return Result.failure(IllegalArgumentException(nombreResult.errorMessage))
        }

        // 2. Validar Descripción (Obligatorio)
        val descripcionResult = validateDescripcion(tipoPenalidad.descripcion)
        if (!descripcionResult.isValid) {
            return Result.failure(IllegalArgumentException(descripcionResult.errorMessage))
        }

        // 3. Validar Puntos (Obligatorio y mayor a cero)
        val puntosResult = validatePuntosDescuento(tipoPenalidad.puntosDescuento)
        if (!puntosResult.isValid) {
            return Result.failure(IllegalArgumentException(puntosResult.errorMessage))
        }


            val listaActual = repository.observeTiposPenalidades().first()
            val existe = listaActual.any {
                it.nombre.equals(tipoPenalidad.nombre, ignoreCase = true) &&
                        it.tipoId != tipoPenalidad.tipoId
            }

            if (existe) {
                return Result.failure(IllegalArgumentException("Ya existe un tipo de penalidad registrado con este nombre"))
            }
            return runCatching { repository.upsert(tipoPenalidad) }
        }
    }