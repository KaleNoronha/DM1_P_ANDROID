package com.example.dm1_p_android.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Gestor de sesión de usuario usando SharedPreferences
 * 
 * Esta clase maneja:
 * - Login y logout de usuarios
 * - Persistencia de datos de sesión
 * - Verificación de estado de login
 * 
 * Los datos se almacenan en SharedPreferences y persisten entre reinicios de la app
 * 
 * @param context Contexto de la aplicación
 * @author Tu Nombre
 * @version 1.0
 */
class SessionManager(context: Context) {
    // SharedPreferences para almacenar datos de sesión
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    
    companion object {
        // Claves para almacenar datos en SharedPreferences
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_EMAIL = "user_email"
        const val KEY_USER_NAME = "user_name"
    }
    
    /**
     * Inicia sesión y guarda los datos del usuario
     * 
     * @param email Email del usuario
     * @param name Nombre del usuario
     * 
     * Ejemplo de uso:
     * ```
     * sessionManager.login("user@test.com", "Usuario")
     * ```
     */
    fun login(email: String, name: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            apply() // Guarda los cambios de forma asíncrona
        }
    }
    
    /**
     * Cierra la sesión y elimina todos los datos guardados
     * 
     * Ejemplo de uso:
     * ```
     * sessionManager.logout()
     * startActivity(Intent(this, LoginActivity::class.java))
     * finish()
     * ```
     */
    fun logout() {
        prefs.edit().clear().apply() // Limpia todos los datos de sesión
    }
    
    /**
     * Verifica si hay una sesión activa
     * 
     * @return true si el usuario está logueado, false en caso contrario
     * 
     * Ejemplo de uso:
     * ```
     * if (sessionManager.isLoggedIn()) {
     *     // Ir a MainActivity
     * } else {
     *     // Ir a LoginActivity
     * }
     * ```
     */
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    
    /**
     * Obtiene el email del usuario logueado
     * 
     * @return Email del usuario o string vacío si no hay sesión
     */
    fun getUserEmail(): String = prefs.getString(KEY_USER_EMAIL, "") ?: ""
    
    /**
     * Obtiene el nombre del usuario logueado
     * 
     * @return Nombre del usuario o string vacío si no hay sesión
     */
    fun getUserName(): String = prefs.getString(KEY_USER_NAME, "") ?: ""
}