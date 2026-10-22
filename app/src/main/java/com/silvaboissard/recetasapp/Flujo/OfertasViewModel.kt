package com.silvaboissard.recetasapp.flujo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class OfertasViewModel(
    fuentes: FuentesOfertas = FuentesOfertas(fallarEnPrimerIntento = true),
) : ViewModel() {

    // stateIn convierte el Flow frío en un StateFlow (siempre tiene un valor actual).
    // - viewModelScope: el scope se cancela solo cuando el ViewModel muere
    //   (structured concurrency, nada de GlobalScope).
    // - WhileSubscribed(5_000): solo trabaja mientras la UI observa; si la UI se
    //   va, espera 5 s (por si fue una rotación) antes de detener el pipeline.
    // - EstadoOferta.Cargando: valor inicial.
    val estado: StateFlow<EstadoOferta> =
        construirPipeline(fuentes, "Brownie de chocolate")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = EstadoOferta.Cargando,
            )
}