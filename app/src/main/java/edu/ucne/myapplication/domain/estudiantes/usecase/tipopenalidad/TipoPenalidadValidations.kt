package edu.ucne.myapplication.domain.estudiantes.usecase.tipopenalidad

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validateNombrePenalidad(value: String): ValidationResult {
    if (value.isBlank()) {
        return ValidationResult(false, "Campo Obligatorio")
    }
    return ValidationResult(true)
}

fun validateDescripcion(value: String): ValidationResult {
    if (value.isBlank()) {
        return ValidationResult(false, "Campo Obligatorio")
    }
    return ValidationResult(true)
}

fun validatePuntosDescuento(value: Int?): ValidationResult {
    if (value == null) {
        return ValidationResult(false, "Campo Obligatorio")
    }
    if (value <= 0) {
        return ValidationResult(false, "Puntos de descuento deben ser mayor a cero")
    }
    return ValidationResult(true)
}