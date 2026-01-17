package edu.ucne.myapplication.data.estudiantes.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Estudiantes")
data class EstudianteEntity(
    @PrimaryKey(autoGenerate = true)
    val estudianteId: Int? = null,
    val nombres: String,
    val email: String,
    val edad: Int? = null,
)