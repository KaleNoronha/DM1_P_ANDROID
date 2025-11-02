package com.example.dm1_p_android.entity

/**
 * Modelo de datos para usuarios del sistema
 * 
 * Representa un usuario registrado en la aplicación con:
 * - Información personal (nombres, apellidos, celular)
 * - Credenciales de acceso (correo, clave)
 * - Datos adicionales (sexo, nombre de usuario)
 * 
 * Los usuarios se almacenan en la base de datos local SQLite
 * y se usan para el sistema de autenticación.
 * 
 * @property id ID único del usuario (auto-generado)
 * @property nombres Nombres del usuario
 * @property apellidos Apellidos del usuario
 * @property nomUSU Nombre de usuario (username)
 * @property celular Número de celular del usuario
 * @property sexo Sexo del usuario (M/F)
 * @property correo Correo electrónico (usado para login)
 * @property clave Contraseña del usuario (debe estar encriptada en producción)
 * 
 * Ejemplo de uso:
 * ```kotlin
 * val usuario = Usuario(
 *     id = 1,
 *     nombres = "Juan",
 *     apellidos = "Pérez",
 *     nomUSU = "jperez",
 *     celular = "987654321",
 *     sexo = "M",
 *     correo = "juan@test.com",
 *     clave = "password123"
 * )
 * ```
 * 
 * NOTA: En producción, la clave debe estar encriptada usando
 * algoritmos como BCrypt o SHA-256
 */
data class Usuario (
    val id: Int = 0,                // ID del usuario
    val nombres : String = "",      // Nombres del usuario
    val apellidos : String = "",    // Apellidos del usuario
    val nomUSU : String = "",       // Nombre de usuario
    val celular:String = "",        // Número de celular
    val sexo : String = "",         // Sexo (M/F)
    val correo : String = "",       // Correo electrónico
    val clave : String = ""         // Contraseña (debe estar encriptada)
) {
    // Constructor sin argumentos requerido por Firebase
    constructor() : this(0, "", "", "", "", "", "", "")
}

