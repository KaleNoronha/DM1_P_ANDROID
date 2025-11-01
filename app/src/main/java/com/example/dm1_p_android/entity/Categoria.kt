package com.example.dm1_p_android.entity

/**
 * Modelo de datos para categorías de productos
 * 
 * Representa una categoría que puede ser asignada a productos.
 * Las categorías se almacenan en la base de datos local SQLite
 * y se usan para organizar y filtrar productos.
 * 
 * @property idCat ID único de la categoría (auto-generado por la BD)
 * @property nomCat Nombre de la categoría
 * 
 * Ejemplo de uso:
 * ```kotlin
 * val categoria = Categoria(
 *     idCat = 1,
 *     nomCat = "Electrónicos"
 * )
 * 
 * // Para insertar nueva categoría (ID se genera automáticamente)
 * val nuevaCategoria = Categoria(
 *     idCat = 0,
 *     nomCat = "Ropa"
 * )
 * categoriaDAO.agregarCategoria(nuevaCategoria)
 * ```
 */
data class Categoria (
    val idCat : Int,            // ID de la categoría
    val nomCat : String = ""    // Nombre de la categoría
)