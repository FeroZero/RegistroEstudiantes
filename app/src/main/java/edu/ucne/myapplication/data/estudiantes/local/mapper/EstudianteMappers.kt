package edu.ucne.myapplication.data.estudiantes.local.mapper

import edu.ucne.myapplication.data.estudiantes.local.entities.EstudianteEntity
import edu.ucne.myapplication.domain.estudiantes.model.Estudiante

fun EstudianteEntity.toDomain(): Estudiante = Estudiante(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad,
)

fun Estudiante.toEntity(): EstudianteEntity = EstudianteEntity(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)