package com.example.dm1_p_android.entity

/**
 * Modelo de datos para productos obtenidos de la API de FakeStore
 * 
 * Esta clase representa un producto con toda su información:
 * - Identificación y detalles básicos
 * - Precio y categoría
 * - Calificación de usuarios
 * 
 * Los datos se obtienen de: https://fakestoreapi.com/products
 * 
 * Ejemplo de JSON:
 * ```json
 * {
 *   "id": 1,
 *   "title": "Fjallraven Backpack",
 *   "price": 109.95,
 *   "description": "Your perfect pack...",
 *   "category": "men's clothing",
 *   "image": "https://...",
 *   "rating": {
 *     "rate": 3.9,
 *     "count": 120
 *   }
 * }
 * ```
 * 
 * @property id Identificador único del producto
 * @property title Nombre/título del producto
 * @property price Precio del producto en dólares
 * @property description Descripción detallada del producto
 * @property category Categoría del producto (electronics, clothing, etc.)
 * @property image URL de la imagen del producto
 * @property rating Objeto con la calificación del producto
 * 
 * @author Tu Nombre
 * @version 1.0
 */
data class ApiProduct(
    val id: Int,              // ID único del producto
    val title: String,        // Nombre del producto
    val price: Double,        // Precio en USD
    val description: String,  // Descripción completa
    val category: String,     // Categoría del producto
    val image: String,        // URL de la imagen
    val rating: Rating        // Calificación del producto
)

/**
 * Modelo de datos para la calificación de un producto
 * 
 * Contiene información sobre cómo los usuarios han calificado el producto:
 * - Promedio de calificación (1.0 a 5.0)
 * - Número total de calificaciones recibidas
 * 
 * @property rate Calificación promedio del producto (escala de 1.0 a 5.0)
 * @property count Número total de usuarios que han calificado el producto
 * 
 * Ejemplo de uso:
 * ```kotlin
 * val rating = Rating(rate = 4.5, count = 250)
 * // Este producto tiene 4.5 estrellas basado en 250 reviews
 * ```
 */
data class Rating(
    val rate: Double,   // Calificación promedio (1.0 - 5.0)
    val count: Int      // Número de calificaciones
)