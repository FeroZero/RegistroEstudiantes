package edu.ucne.myapplication.data.estudiantes.local.mapper

import edu.ucne.myapplication.data.estudiantes.local.entities.AsignaturaEntity
import edu.ucne.myapplication.domain.estudiantes.model.Asignatura

fun AsignaturaEntity.toDomain(): Asignatura = Asignatura(
     AsignaturaId = AsignaturaId,
     Codigo = Codigo,
     Nombre = Nombre,
     Aula = Aula,
     Creditos = Creditos,
)

fun Asignatura.toEntity(): AsignaturaEntity = AsignaturaEntity(
    AsignaturaId = AsignaturaId,
    Codigo = Codigo,
    Nombre = Nombre,
    Aula = Aula,
    Creditos = Creditos,
)