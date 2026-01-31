package edu.ucne.myapplication.domain.estudiantes.usecase.asignaturas

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validateCodigo(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")
    if (value.length < 3) return ValidationResult(false, "Código demasiado corto")
    return ValidationResult(true)
}

fun validateNombreAsignatura(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")
    return ValidationResult(true)
}

fun validateAula(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")
    return ValidationResult(true)
}

fun validateCreditos(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")

    val creditosInt = value.toIntOrNull()
    if (creditosInt == null || creditosInt <= 0) {
        return ValidationResult(false, "Créditos inválidos")
    }
    if (creditosInt > 6) {
        return ValidationResult(false, "Máximo 6 créditos")
    }

    return ValidationResult(true)
}