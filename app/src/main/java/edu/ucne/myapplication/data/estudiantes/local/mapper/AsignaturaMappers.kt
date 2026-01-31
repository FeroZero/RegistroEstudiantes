package edu.ucne.myapplication.data.estudiantes.local.mapper

import edu.ucne.myapplication.data.estudiantes.local.entities.AsignaturaEntity
import edu.ucne.myapplication.domain.estudiantes.model.Asignatura

fun AsignaturaEntity.toDomain(): Asignatura = Asignatura(
     asignaturaId = AsignaturaId,
     codigo = Codigo,
     nombre = Nombre,
     aula = Aula,
     creditos = Creditos,
)

fun Asignatura.toEntity(): AsignaturaEntity = AsignaturaEntity(
    AsignaturaId = asignaturaId,
    Codigo = codigo,
    Nombre = nombre,
    Aula = aula,
    Creditos = creditos,
)