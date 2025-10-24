package com.example.dm1_p_android.data

import android.content.ContentValues
import android.content.Context
import com.example.dm1_p_android.entity.Usuario

class UsuarioDAO(context: Context) {

    private val dbHelper = AppDatabaseHelper(context)

    fun agregarUsuario(usuario : Usuario) : Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", usuario.id)
            put("nombres", usuario.nombres)
            put("apellidos", usuario.apellidos)
            put("nom_usu", usuario.nomUSU)
            put("celular", usuario.celular)
            put("sexo", usuario.sexo)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
        }
        return db.insert("usuarios", null, valores)
    }

}