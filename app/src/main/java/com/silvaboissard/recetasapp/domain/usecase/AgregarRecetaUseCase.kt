package com.silvaboissard.recetasapp.domain.usecase

import com.silvaboissard.recetasapp.domain.repository.RecetaRepository

// Business rule: validation lives here, not in the screen.
class AgregarRecetaUseCase(private val repository: RecetaRepository) {
    suspend operator fun invoke(
        nombre: String,
        categoria: String,
        tiempoPrepMinutos: Int,
    ): Result<Unit> {
        if (nombre.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }
        if (categoria.isBlank()) {
            return Result.failure(IllegalArgumentException("La categoría no puede estar vacía"))
        }
        if (tiempoPrepMinutos <= 0) {
            return Result.failure(IllegalArgumentException("El tiempo debe ser mayor a 0"))
        }
        repository.agregar(nombre.trim(), categoria.trim(), tiempoPrepMinutos)
        return Result.success(Unit)
    }
}