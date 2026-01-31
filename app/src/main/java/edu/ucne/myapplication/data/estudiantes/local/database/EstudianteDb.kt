package edu.ucne.myapplication.data.estudiantes.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.myapplication.data.estudiantes.local.dao.AsignaturaDao
import edu.ucne.myapplication.data.estudiantes.local.dao.EstudianteDao
import edu.ucne.myapplication.data.estudiantes.local.dao.TipoPenalidadDao
import edu.ucne.myapplication.data.estudiantes.local.entities.AsignaturaEntity
import edu.ucne.myapplication.data.estudiantes.local.entities.EstudianteEntity
import edu.ucne.myapplication.data.estudiantes.local.entities.TipoPenalidadEntity

@Database(
    entities = [
                EstudianteEntity::class,
                AsignaturaEntity::class,
                TipoPenalidadEntity::class,
               ],
    version = 3,
    exportSchema = false
)

abstract class EstudianteDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun tipoPenalidadDao(): TipoPenalidadDao
}