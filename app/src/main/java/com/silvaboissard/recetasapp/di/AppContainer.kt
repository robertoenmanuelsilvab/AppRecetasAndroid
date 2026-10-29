package com.silvaboissard.recetasapp.di

import com.silvaboissard.recetasapp.data.RecetaRepositoryImpl
import com.silvaboissard.recetasapp.domain.repository.RecetaRepository
import com.silvaboissard.recetasapp.domain.usecase.AgregarRecetaUseCase
import com.silvaboissard.recetasapp.domain.usecase.ObtenerRecetaPorIdUseCase
import com.silvaboissard.recetasapp.domain.usecase.ObtenerRecetasUseCase

// Es el único lugar que conoce la implementación concreta del repositorio.
// Crea UNA sola instancia y se la entrega a todos los UseCases. Si cada
// ViewModel creara su propio repositorio, cada pantalla vería datos distintos.
// (Más adelante esto se reemplaza por Hilt/Koin.)
object AppContainer {
    private val repository: RecetaRepository by lazy { RecetaRepositoryImpl() }

    val obtenerRecetas: ObtenerRecetasUseCase by lazy { ObtenerRecetasUseCase(repository) }
    val obtenerRecetaPorId: ObtenerRecetaPorIdUseCase by lazy { ObtenerRecetaPorIdUseCase(repository) }
    val agregarReceta: AgregarRecetaUseCase by lazy { AgregarRecetaUseCase(repository) }
}