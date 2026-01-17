package edu.ucne.myapplication.data.estudiantes.local.repositoryimpl

import edu.ucne.myapplication.data.estudiantes.local.dao.EstudianteDao
import edu.ucne.myapplication.data.estudiantes.local.mapper.toDomain
import edu.ucne.myapplication.data.estudiantes.local.mapper.toEntity
import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import edu.ucne.myapplication.domain.estudiantes.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EstudianteRepositoryImpl(
    private val dao: EstudianteDao
) : EstudianteRepository {
    override fun observeEstudiante(): Flow<List<Estudiante>> = dao.observeALL().map { list ->
        list.map { it.toDomain() }
    }
    override suspend fun getEstudiante(id: Int): Estudiante? = dao.getById(id)?.toDomain()

    override suspend fun upsert(estudiante: Estudiante): Int {
        dao.upsert(entity = estudiante.toEntity())
        return estudiante.estudianteId ?: 0
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}