package com.silvaboissard.recetasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silvaboissard.recetasapp.di.AppContainer
import com.silvaboissard.recetasapp.domain.usecase.ObtenerRecetasUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class InicioUiState(val categorias: List<String> = emptyList())

class InicioViewModel(
    obtenerRecetas: ObtenerRecetasUseCase = AppContainer.obtenerRecetas,
) : ViewModel() {

    val uiState: StateFlow<InicioUiState> = obtenerRecetas()
        .map { lista -> InicioUiState(lista.map { it.categoria }.distinct()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), InicioUiState())
}