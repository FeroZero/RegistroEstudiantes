package edu.ucne.myapplication.data.estudiantes.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Asignaturas")
data class AsignaturaEntity (
    @PrimaryKey(autoGenerate = true)
    val AsignaturaId: Int? = null,
    val Codigo: String,
    val Nombre: String,
    val Aula: String,
    val Creditos: String,
)