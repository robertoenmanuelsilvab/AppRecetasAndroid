package com.silvaboissard.recetasapp.domain.repository

import com.silvaboissard.recetasapp.domain.model.Receta
import kotlinx.coroutines.flow.Flow

// The domain only defines WHAT it needs. HOW it is stored (memory, Room,
// Retrofit) is the data layer's decision.
interface RecetaRepository {
    fun observarRecetas(): Flow<List<Receta>>
    suspend fun obtenerPorId(id: Int): Receta?
    suspend fun agregar(nombre: String, categoria: String, tiempoPrepMinutos: Int)
}