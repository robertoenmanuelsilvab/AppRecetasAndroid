package com.silvaboissard.recetasapp.data

data class Receta(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val tiempoPrepMinutos: Int,
    val ingredientes: List<String>,
    val pasos: List<String>,
)

val recetasDeMuestra = listOf(
    Receta(
        id = 1,
        nombre = "Tostadas francesas",
        categoria = "Desayuno",
        tiempoPrepMinutos = 15,
        ingredientes = listOf("4 rebanadas de pan", "2 huevos", "1/2 taza de leche", "Canela al gusto"),
        pasos = listOf(
            "Bate los huevos con la leche y la canela.",
            "Sumerge cada rebanada de pan en la mezcla.",
            "Cocina en sartén caliente hasta dorar por ambos lados.",
        ),
    ),
    Receta(
        id = 2,
        nombre = "Ensalada César",
        categoria = "Almuerzo",
        tiempoPrepMinutos = 20,
        ingredientes = listOf("Lechuga romana", "Pollo a la plancha", "Crutones", "Queso parmesano", "Aderezo César"),
        pasos = listOf(
            "Lava y corta la lechuga.",
            "Agrega el pollo en tiras y los crutones.",
            "Mezcla con el aderezo y espolvorea el queso.",
        ),
    ),
    Receta(
        id = 3,
        nombre = "Brownie de chocolate",
        categoria = "Postre",
        tiempoPrepMinutos = 40,
        ingredientes = listOf("200g chocolate", "150g mantequilla", "3 huevos", "1 taza de azúcar", "1 taza de harina"),
        pasos = listOf(
            "Derrite el chocolate con la mantequilla.",
            "Mezcla con azúcar, huevos y harina.",
            "Hornea a 180°C por 25 minutos.",
        ),
    ),
    Receta(
        id = 4,
        nombre = "Sopa de vegetales",
        categoria = "Almuerzo",
        tiempoPrepMinutos = 30,
        ingredientes = listOf("Zanahoria", "Papa", "Apio", "Caldo de vegetales", "Sal y pimienta"),
        pasos = listOf(
            "Corta todos los vegetales en cubos.",
            "Cocina en el caldo por 20 minutos.",
            "Sazona al gusto y sirve caliente.",
        ),
    ),
    Receta(
        id = 5,
        nombre = "Batido de fresa",
        categoria = "Desayuno",
        tiempoPrepMinutos = 5,
        ingredientes = listOf("1 taza de fresas", "1 banana", "1 taza de leche", "Miel al gusto"),
        pasos = listOf(
            "Coloca todos los ingredientes en la licuadora.",
            "Licúa hasta obtener una mezcla homogénea.",
            "Sirve frío.",
        ),
    ),
)