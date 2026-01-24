package edu.ucne.myapplication.data.estudiantes.local.repositoryimpl

import edu.ucne.myapplication.data.estudiantes.local.dao.AsignaturaDao
import edu.ucne.myapplication.data.estudiantes.local.mapper.toDomain
import edu.ucne.myapplication.data.estudiantes.local.mapper.toEntity
import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import edu.ucne.myapplication.domain.estudiantes.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val dao: AsignaturaDao
) : AsignaturaRepository {

    override fun observeAsignatura(): Flow<List<Asignatura>> = dao.observeALL().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getAsignatura(id: Int): Asignatura? = dao.getById(id)?.toDomain()

    override suspend fun upsert(asignatura: Asignatura): Int {
        dao.upsert(entity = asignatura.toEntity())
        return asignatura.AsignaturaId ?: 0
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}