package com.silvaboissard.recetasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silvaboissard.recetasapp.domain.model.Receta
import com.silvaboissard.recetasapp.domain.usecase.ObtenerRecetaPorIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DetalleUiState {
    object Cargando : DetalleUiState
    data class Encontrada(val receta: Receta) : DetalleUiState
    object NoEncontrada : DetalleUiState
}

class DetalleViewModel(
    recetaId: Int,
    obtenerRecetaPorId: ObtenerRecetaPorIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val receta = obtenerRecetaPorId(recetaId)
            _uiState.value =
                if (receta != null) DetalleUiState.Encontrada(receta) else DetalleUiState.NoEncontrada
        }
    }
}