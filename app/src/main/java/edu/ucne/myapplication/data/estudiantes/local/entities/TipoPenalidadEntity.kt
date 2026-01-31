package edu.ucne.myapplication.data.estudiantes.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TiposPenalidades")
data class TipoPenalidadEntity(
    @PrimaryKey(autoGenerate = true)
    val TipoId: Int? = null,
    val Nombre: String,
    val Descripcion: String,
    val PuntosDescuento: Int
)