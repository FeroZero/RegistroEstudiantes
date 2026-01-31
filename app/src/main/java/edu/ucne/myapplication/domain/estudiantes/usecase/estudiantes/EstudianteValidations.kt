package edu.ucne.myapplication.domain.estudiantes.usecase.estudiantes

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validateNombres(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")
    return ValidationResult(true)
}

fun validateEmail(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Campo Obligatorio")
    if (!value.contains("@")) return ValidationResult(false, "Email Invalido")
    return ValidationResult(true)
}

fun validateEdad(value: Int?): ValidationResult {
    if (value == null) return ValidationResult(false, "Campo Obligatorio")
    if (value <=0) return ValidationResult(false, "Edad Invalida")
    if (value > 100) return ValidationResult(false, "Edad Invalida")
    return ValidationResult(true)

}