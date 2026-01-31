package edu.ucne.myapplication.data.estudiantes.local.repositoryimpl

import edu.ucne.myapplication.data.estudiantes.local.dao.TipoPenalidadDao
import edu.ucne.myapplication.data.estudiantes.local.mapper.toDomain
import edu.ucne.myapplication.data.estudiantes.local.mapper.toEntity
import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad
import edu.ucne.myapplication.domain.estudiantes.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {

    override fun observeTiposPenalidades(): Flow<List<TipoPenalidad>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getTipoPenalidad(id: Int): TipoPenalidad? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(tipoPenalidad: TipoPenalidad): Int {
        dao.upsert(entity = tipoPenalidad.toEntity())
        return tipoPenalidad.tipoId ?: 0
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}