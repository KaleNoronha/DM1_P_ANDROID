package com.example.dm1_p_android.entity

/**
 * Modelo de datos para productos locales del inventario
 * 
 * Esta clase representa un producto creado localmente por el usuario:
 * - Información básica del producto
 * - Control de stock y unidades
 * - Precio y categoría
 * 
 * Se usa principalmente en AgregarProductoActivity para productos
 * creados manualmente por el usuario
 * 
 * @property idProd ID único del producto
 * @property nomProd Nombre del producto
 * @property codProd Código de identificación del producto
 * @property stoProd Stock/cantidad disponible (como String para flexibilidad)
 * @property uniMedida Unidad de medida (Kg, L, Unidades, etc.)
 * @property preProd Precio del producto
 * @property desProd Descripción del producto
 * @property Categoria Categoría del producto
 * 
 * Ejemplo de uso:
 * ```kotlin
 * val producto = Producto(
 *     idProd = 1,
 *     nomProd = "Laptop HP",
 *     codProd = "LAP001",
 *     stoProd = "10",
 *     uniMedida = 1.0,
 *     preProd = 599.99,
 *     desProd = "Laptop para oficina",
 *     Categoria = "Electrónicos"
 * )
 * ```
 */
data class Producto (
    val idProd : Int = 0,           // ID del producto
    val nomProd : String = "",      // Nombre del producto
    val codProd : String = "",      // Código del producto
    val stoProd : String = "",      // Stock disponible
    val uniMedida : Double = 0.0,   // Unidad de medida
    val preProd : Double = 0.0,     // Precio del producto
    val desProd : String = "",      // Descripción
    val Categoria : String=""       // Categoría
)