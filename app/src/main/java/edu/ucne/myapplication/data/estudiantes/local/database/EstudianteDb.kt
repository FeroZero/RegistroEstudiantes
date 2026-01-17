package edu.ucne.myapplication.data.estudiantes.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.myapplication.data.estudiantes.local.dao.EstudianteDao
import edu.ucne.myapplication.data.estudiantes.local.entities.EstudianteEntity

@Database(
    entities = [
                EstudianteEntity::class,
               ],
    version = 1,
    exportSchema = false
)

abstract class EstudianteDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
}