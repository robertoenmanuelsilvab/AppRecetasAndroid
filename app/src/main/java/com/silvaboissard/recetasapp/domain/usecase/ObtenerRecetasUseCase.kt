package com.silvaboissard.recetasapp.domain.usecase

import com.silvaboissard.recetasapp.domain.model.Receta
import com.silvaboissard.recetasapp.domain.repository.RecetaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Business rule: the list can optionally be filtered by category.
class ObtenerRecetasUseCase(private val repository: RecetaRepository) {
    operator fun invoke(categoria: String? = null): Flow<List<Receta>> =
        repository.observarRecetas().map { lista ->
            if (categoria == null) lista else lista.filter { it.categoria == categoria }
        }
}