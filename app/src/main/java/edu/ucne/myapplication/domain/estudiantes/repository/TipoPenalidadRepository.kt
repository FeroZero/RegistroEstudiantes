package edu.ucne.myapplication.domain.estudiantes.repository

import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    fun observeTiposPenalidades(): Flow<List<TipoPenalidad>>

    suspend fun getTipoPenalidad(id: Int): TipoPenalidad?

    suspend fun upsert(tipoPenalidad: TipoPenalidad): Int

    suspend fun delete(id: Int)
}