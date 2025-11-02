package com.example.dm1_p_android.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.dm1_p_android.entity.Usuario

class UsuarioDAO(context: Context) {

    private val dbHelper = AppDatabaseHelper(context)

    // ==================== OPERACIONES BÁSICAS (CRUD) ====================

    /**
     * Lista todos los usuarios ordenados por ID descendente
     */
    fun listarUsuarios(): List<Usuario> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Usuario>()
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios ORDER BY id DESC",
            null
        )

        while (cursor.moveToNext()) {
            lista.add(cursorAUsuario(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    /**
     * Agrega un nuevo usuario a la base de datos
     * @return El ID del usuario insertado o -1 si falla
     */
    fun agregarUsuario(usuario: Usuario): Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombres", usuario.nombres)
            put("apellidos", usuario.apellidos)
            put("nom_usu", usuario.nomUSU)
            put("celular", usuario.celular)
            put("sexo", usuario.sexo)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
        }
        val resultado = db.insert("usuarios", null, valores)
        db.close()
        return resultado
    }

    /**
     * Actualiza los datos de un usuario existente
     * @return Número de filas actualizadas
     */
    fun actualizarUsuario(usuario: Usuario): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombres", usuario.nombres)
            put("apellidos", usuario.apellidos)
            put("nom_usu", usuario.nomUSU)
            put("celular", usuario.celular)
            put("sexo", usuario.sexo)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
        }
        val resultado = db.update(
            "usuarios",
            valores,
            "id = ?",
            arrayOf(usuario.id.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Elimina un usuario de la base de datos
     * @return Número de filas eliminadas
     */
    fun eliminarUsuario(usuarioId: Int): Int {
        val db = dbHelper.writableDatabase
        val resultado = db.delete(
            "usuarios",
            "id = ?",
            arrayOf(usuarioId.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Elimina todos los usuarios
     */
    fun eliminarTodosUsuarios(): Int {
        val db = dbHelper.writableDatabase
        val resultado = db.delete("usuarios", null, null)
        db.close()
        return resultado
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Obtiene un usuario por su ID
     */
    fun obtenerUsuarioPorId(id: Int): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE id = ?",
            arrayOf(id.toString())
        )

        val usuario = if (cursor.moveToFirst()) {
            cursorAUsuario(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return usuario
    }

    /**
     * Obtiene un usuario por su nombre de usuario
     */
    fun obtenerUsuarioPorNombreUsuario(nombreUsuario: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nom_usu = ? LIMIT 1",
            arrayOf(nombreUsuario)
        )

        val usuario = if (cursor.moveToFirst()) {
            cursorAUsuario(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return usuario
    }

    /**
     * Obtiene un usuario por su correo electrónico
     */
    fun obtenerUsuarioPorCorreo(correo: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE correo = ? LIMIT 1",
            arrayOf(correo)
        )

        val usuario = if (cursor.moveToFirst()) {
            cursorAUsuario(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return usuario
    }

    // ==================== BÚSQUEDAS Y FILTROS ====================

    /**
     * Busca usuarios por nombre, apellido, usuario, correo o celular
     */
    fun buscarUsuarios(busqueda: String): List<Usuario> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Usuario>()
        val patron = "%$busqueda%"

        val cursor = db.rawQuery(
            """
            SELECT * FROM usuarios 
            WHERE nombres LIKE ? 
            OR apellidos LIKE ? 
            OR nom_usu LIKE ?
            OR correo LIKE ?
            OR celular LIKE ?
            ORDER BY id DESC
            """,
            arrayOf(patron, patron, patron, patron, patron)
        )

        while (cursor.moveToNext()) {
            lista.add(cursorAUsuario(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    /**
     * Obtiene usuarios filtrados por sexo
     */
    fun obtenerUsuariosPorSexo(sexo: String): List<Usuario> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Usuario>()
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE sexo = ? ORDER BY id DESC",
            arrayOf(sexo)
        )

        while (cursor.moveToNext()) {
            lista.add(cursorAUsuario(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    /**
     * Obtiene usuarios ordenados por nombre
     */
    fun obtenerUsuariosOrdenadosPorNombre(): List<Usuario> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Usuario>()
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios ORDER BY nombres ASC, apellidos ASC",
            null
        )

        while (cursor.moveToNext()) {
            lista.add(cursorAUsuario(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    // ==================== AUTENTICACIÓN ====================

    /**
     * Autentica un usuario con nombre de usuario y contraseña
     */
    fun autenticarUsuario(nombreUsuario: String, clave: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nom_usu = ? AND clave = ? LIMIT 1",
            arrayOf(nombreUsuario, clave)
        )

        val usuario = if (cursor.moveToFirst()) {
            cursorAUsuario(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return usuario
    }

    /**
     * Autentica un usuario con correo y contraseña
     */
    fun autenticarPorCorreo(correo: String, clave: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE correo = ? AND clave = ? LIMIT 1",
            arrayOf(correo, clave)
        )

        val usuario = if (cursor.moveToFirst()) {
            cursorAUsuario(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return usuario
    }

    // ==================== VALIDACIONES ====================

    /**
     * Verifica si existe un nombre de usuario
     */
    fun existeNombreUsuario(nombreUsuario: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM usuarios WHERE nom_usu = ?",
            arrayOf(nombreUsuario)
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    /**
     * Verifica si existe un correo electrónico
     */
    fun existeCorreo(correo: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM usuarios WHERE correo = ?",
            arrayOf(correo)
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    /**
     * Verifica si existe un nombre de usuario excluyendo un ID
     * Útil para validar en edición
     */
    fun existeNombreUsuarioExcluyendo(nombreUsuario: String, usuarioId: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM usuarios WHERE nom_usu = ? AND id != ?",
            arrayOf(nombreUsuario, usuarioId.toString())
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    /**
     * Verifica si existe un correo excluyendo un ID
     */
    fun existeCorreoExcluyendo(correo: String, usuarioId: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM usuarios WHERE correo = ? AND id != ?",
            arrayOf(correo, usuarioId.toString())
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    // ==================== CONTEOS Y ESTADÍSTICAS ====================

    /**
     * Cuenta el total de usuarios registrados
     */
    fun contarUsuarios(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM usuarios", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        db.close()
        return count
    }

    /**
     * Cuenta usuarios por sexo
     */
    fun contarUsuariosPorSexo(sexo: String): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM usuarios WHERE sexo = ?",
            arrayOf(sexo)
        )
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        db.close()
        return count
    }

    // ==================== OPERACIONES AVANZADAS ====================

    /**
     * Actualiza solo la contraseña de un usuario
     */
    fun actualizarClave(usuarioId: Int, nuevaClave: String): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("clave", nuevaClave)
        }
        val resultado = db.update(
            "usuarios",
            valores,
            "id = ?",
            arrayOf(usuarioId.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Actualiza el celular de un usuario
     */
    fun actualizarCelular(usuarioId: Int, nuevoCelular: String): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("celular", nuevoCelular)
        }
        val resultado = db.update(
            "usuarios",
            valores,
            "id = ?",
            arrayOf(usuarioId.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Inserta usuarios de prueba iniciales
     */
    fun insertarUsuariosIniciales() {
        val db = dbHelper.writableDatabase

        val cursor = db.rawQuery("SELECT COUNT(*) FROM usuarios", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()

        if (count == 0) {
            val usuariosIniciales = listOf(
                Usuario(0, "Juan", "Pérez", "juanp", "987654321", "M", "juan@email.com", "123456"),
                Usuario(0, "María", "García", "mariag", "987654322", "F", "maria@email.com", "123456"),
                Usuario(0, "Admin", "Sistema", "admin", "999999999", "M", "admin@email.com", "admin123")
            )

            usuariosIniciales.forEach { usuario ->
                val valores = ContentValues().apply {
                    put("nombres", usuario.nombres)
                    put("apellidos", usuario.apellidos)
                    put("nom_usu", usuario.nomUSU)
                    put("celular", usuario.celular)
                    put("sexo", usuario.sexo)
                    put("correo", usuario.correo)
                    put("clave", usuario.clave)
                }
                db.insert("usuarios", null, valores)
            }
        }

        db.close()
    }

    // ==================== MÉTODO AUXILIAR ====================

    /**
     * Convierte un Cursor en un objeto Usuario
     */
    private fun cursorAUsuario(cursor: Cursor): Usuario {
        return Usuario(
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres")) ?: "",
            apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos")) ?: "",
            nomUSU = cursor.getString(cursor.getColumnIndexOrThrow("nom_usu")) ?: "",
            celular = cursor.getString(cursor.getColumnIndexOrThrow("celular")) ?: "",
            sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")) ?: "",
            correo = cursor.getString(cursor.getColumnIndexOrThrow("correo")) ?: "",
            clave = cursor.getString(cursor.getColumnIndexOrThrow("clave")) ?: ""
        )
    }
}