package edu.ucne.myapplication.domain.estudiantes.repository

import edu.ucne.myapplication.domain.estudiantes.model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {
    fun observeEstudiante(): Flow<List<Estudiante>>

    suspend fun getEstudiante(id: Int): Estudiante?

    suspend fun upsert(estudiante: Estudiante): Int

    suspend fun delete(id: Int)

}