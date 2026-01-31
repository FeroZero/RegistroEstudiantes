package edu.ucne.myapplication.data.estudiantes.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.myapplication.data.estudiantes.local.entities.TipoPenalidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {
    @Query(value = "SELECT * FROM TiposPenalidades ORDER BY TipoId DESC")
    fun observeAll(): Flow<List<TipoPenalidadEntity>>

    @Query(value = "SELECT * FROM TiposPenalidades WHERE TipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?

    @Upsert
    suspend fun upsert(entity: TipoPenalidadEntity)

    @Delete
    suspend fun delete(entity: TipoPenalidadEntity)

    @Query(value = "DELETE FROM TiposPenalidades WHERE TipoId = :id")
    suspend fun deleteById(id: Int)
}