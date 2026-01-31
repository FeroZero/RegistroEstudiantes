package edu.ucne.myapplication.data.estudiantes.local.mapper

import edu.ucne.myapplication.data.estudiantes.local.entities.TipoPenalidadEntity
import edu.ucne.myapplication.domain.estudiantes.model.TipoPenalidad

fun TipoPenalidadEntity.toDomain(): TipoPenalidad = TipoPenalidad(
    tipoId = TipoId,
    nombre = Nombre,
    descripcion = Descripcion,
    puntosDescuento = PuntosDescuento
)

fun TipoPenalidad.toEntity(): TipoPenalidadEntity = TipoPenalidadEntity(
    TipoId = tipoId,
    Nombre = nombre,
    Descripcion = descripcion,
    PuntosDescuento = puntosDescuento
)