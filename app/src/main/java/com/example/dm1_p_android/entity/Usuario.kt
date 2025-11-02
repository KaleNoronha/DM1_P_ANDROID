package com.example.dm1_p_android.entity


data class Usuario (
    val id: Int,                    // ID del usuario
    val nombres : String = "",      // Nombres del usuario
    val apellidos : String = "",    // Apellidos del usuario
    val nomUSU : String = "",       // Nombre de usuario
    val celular:String = "",        // Número de celular
    val sexo : String = "",         // Sexo (M/F)
    val correo : String = "",       // Correo electrónico
    val clave : String = ""         // Contraseña (debe estar encriptada)
)

