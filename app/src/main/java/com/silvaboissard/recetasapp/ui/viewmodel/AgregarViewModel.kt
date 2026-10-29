package com.silvaboissard.recetasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silvaboissard.recetasapp.di.AppContainer
import com.silvaboissard.recetasapp.domain.usecase.AgregarRecetaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AgregarUiState(
    val nombre: String = "",
    val categoria: String = "",
    val tiempo: String = "",
    val error: String? = null,
)

class AgregarViewModel(
    private val agregarReceta: AgregarRecetaUseCase = AppContainer.agregarReceta,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgregarUiState())
    val uiState: StateFlow<AgregarUiState> = _uiState.asStateFlow()

    // Eventos que SUBEN desde la pantalla ("events up")
    fun onNombreChange(valor: String) = _uiState.update { it.copy(nombre = valor, error = null) }
    fun onCategoriaChange(valor: String) = _uiState.update { it.copy(categoria = valor, error = null) }
    fun onTiempoChange(valor: String) = _uiState.update { it.copy(tiempo = valor, error = null) }

    fun guardar(onExito: () -> Unit) {
        val estado = _uiState.value
        val tiempo = estado.tiempo.toIntOrNull()
        if (tiempo == null) {
            _uiState.update { it.copy(error = "El tiempo debe ser un número") }
            return
        }
        viewModelScope.launch {
            // La validación de negocio vive en el UseCase, no aquí ni en la pantalla.
            agregarReceta(estado.nombre, estado.categoria, tiempo)
                .onSuccess { onExito() }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }
}