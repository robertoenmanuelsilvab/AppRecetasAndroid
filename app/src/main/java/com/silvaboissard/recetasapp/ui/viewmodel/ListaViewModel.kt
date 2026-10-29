package com.silvaboissard.recetasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silvaboissard.recetasapp.domain.model.Receta
import com.silvaboissard.recetasapp.domain.usecase.ObtenerRecetasUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ListaUiState(
    val cargando: Boolean = true,
    val recetas: List<Receta> = emptyList(),
)

class ListaViewModel(
    categoria: String?,
    obtenerRecetas: ObtenerRecetasUseCase,
) : ViewModel() {

    val uiState: StateFlow<ListaUiState> = obtenerRecetas(categoria)
        .map { ListaUiState(cargando = false, recetas = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ListaUiState())
}