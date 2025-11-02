package com.example.dm1_p_android.network

import android.util.Log
import com.example.dm1_p_android.entity.Usuario
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseUsuarioService {
    
    private val database = FirebaseDatabase.getInstance("https://inventario-2f11e-default-rtdb.firebaseio.com")
    private val usuariosRef = database.getReference("usuarios")
    
    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        return try {
            Log.d("FirebaseUsuario", "Intentando registrar usuario: ${usuario.correo}")
            val usuarioMap = hashMapOf(
                "id" to usuario.id,
                "nombres" to usuario.nombres,
                "apellidos" to usuario.apellidos,
                "nomUSU" to usuario.nomUSU,
                "celular" to usuario.celular,
                "sexo" to usuario.sexo,
                "correo" to usuario.correo,
                "clave" to usuario.clave
            )
            usuariosRef.child(usuario.id.toString()).setValue(usuarioMap).await()
            Log.d("FirebaseUsuario", "Usuario registrado exitosamente en Firebase")
            true
        } catch (e: Exception) {
            Log.e("FirebaseUsuario", "Error al registrar usuario: ${e.message}", e)
            false
        }
    }
    
    suspend fun validarCredenciales(correo: String, clave: String): Usuario? {
        return try {
            Log.d("FirebaseUsuario", "Validando credenciales para: $correo")
            val snapshot = usuariosRef.orderByChild("correo").equalTo(correo).get().await()
            snapshot.children.firstOrNull()?.let { child ->
                val usuario = child.getValue(Usuario::class.java)
                if (usuario?.clave == clave) {
                    Log.d("FirebaseUsuario", "Credenciales válidas")
                    usuario
                } else {
                    Log.d("FirebaseUsuario", "Contraseña incorrecta")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("FirebaseUsuario", "Error al validar credenciales: ${e.message}")
            null
        }
    }
}
