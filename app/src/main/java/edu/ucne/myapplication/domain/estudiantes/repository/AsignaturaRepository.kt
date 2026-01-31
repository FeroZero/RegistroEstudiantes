package edu.ucne.myapplication.domain.estudiantes.repository

import edu.ucne.myapplication.domain.estudiantes.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun observeAsignatura(): Flow<List<Asignatura>>

    suspend fun getAsignatura(id: Int): Asignatura?

    suspend fun upsert(Asignatura: Asignatura): Int

    suspend fun delete(id: Int)

}