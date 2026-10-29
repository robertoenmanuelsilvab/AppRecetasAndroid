package com.silvaboissard.recetasapp.domain.model

data class Receta(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val tiempoPrepMinutos: Int,
    val ingredientes: List<String>,
    val pasos: List<String>,
)