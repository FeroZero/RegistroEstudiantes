package edu.ucne.myapplication.data.estudiantes.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.myapplication.data.estudiantes.local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Query(value = "SELECT * FROM Asignaturas ORDER BY AsignaturaId DESC")
    fun observeALL(): Flow<List<AsignaturaEntity>>

    @Query(value = "SELECT * FROM Asignaturas WHERE AsignaturaId = :id")
    suspend fun getById(id: Int): AsignaturaEntity?

    @Upsert
    suspend fun upsert(entity: AsignaturaEntity)

    @Delete
    suspend fun delete(entity: AsignaturaEntity)

    @Query(value = "DELETE FROM Asignaturas WHERE AsignaturaId = :id")
    suspend fun deleteById(id: Int)
}