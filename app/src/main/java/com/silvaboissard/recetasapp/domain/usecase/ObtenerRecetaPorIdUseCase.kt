package com.silvaboissard.recetasapp.domain.usecase

import com.silvaboissard.recetasapp.domain.model.Receta
import com.silvaboissard.recetasapp.domain.repository.RecetaRepository

class ObtenerRecetaPorIdUseCase(private val repository: RecetaRepository) {
    suspend operator fun invoke(id: Int): Receta? = repository.obtenerPorId(id)
}