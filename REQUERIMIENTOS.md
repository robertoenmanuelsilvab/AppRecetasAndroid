# RecetasApp

## 1. Descripción
RecetasApp es una aplicación que permite al usuario explorar una colección de recetas de cocina organizadas por categoría (desayuno, almuerzo, postre, etc.). El usuario puede navegar desde una pantalla de inicio hacia una lista de recetas y ver el detalle completo de cada una, incluyendo ingredientes y pasos de preparación.

## 2. Problema que resuelve
Muchas personas guardan recetas dispersas en notas, capturas de pantalla o redes sociales, lo que dificulta encontrarlas cuando las necesitan. RecetasApp centraliza las recetas en un solo lugar, organizadas y fáciles de consultar mientras se cocina.

## 3. Pantallas
| # | Nombre de pantalla | Descripción breve |
|---|-------------------|-------------------------------|
| 1 | PantallaInicio | Bienvenida con categorías de recetas (LazyRow) y acceso a la lista completa |
| 2 | PantallaLista | Lista scrollable de todas las recetas (LazyColumn), con opción de marcar favoritas |
| 3 | PantallaDetalle | Detalle de una receta: ingredientes, pasos y tiempo de preparación |

## 4. Tecnologías usadas
- Kotlin 2.x
- Jetpack Compose + Material 3
- Navigation Compose
- Estado con remember / rememberSaveable

## 5. Diagrama de navegación
PantallaInicio es el punto de entrada. Desde ahí, el usuario puede tocar una categoría o el botón "Ver todas las recetas" para ir a PantallaLista. Desde PantallaLista, al tocar una receta se navega a PantallaDetalle pasando el ID de la receta como argumento. Desde PantallaDetalle, un botón "Regresar" vuelve a PantallaLista mediante popBackStack().

## 6. Capturas de pantalla
[Se agregarán al finalizar el desarrollo]