package com.example.dm1_p_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dm1_p_android.data.AppDatabaseHelper
import com.example.dm1_p_android.network.FirebaseUsuarioService
import com.example.dm1_p_android.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin : Button
    private lateinit var tvIrARegistro: TextView
    private lateinit var tvOlvidastePassword : TextView
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        
        // Verificar si ya está logueado
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        btnLogin= findViewById(R.id.btnLogin)
        tvIrARegistro =findViewById(R.id.tvIrARegistro)
        tvOlvidastePassword  =findViewById(R.id.tvOlvidastePassword)

        btnLogin.setOnClickListener {
            validarCampos()
        }
        tvIrARegistro.setOnClickListener {
            cambioActivity(RegistroActivity::class.java)
        }
        tvOlvidastePassword.setOnClickListener {
            cambioActivity(RecuperarPasswordActivity::class.java)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun cambioActivity(activityDestino : Class<out Activity>) {
        val intent = Intent(this, activityDestino)
        startActivity(intent)
    }
    fun validarCampos(){
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        var error = false
        
        // Validar email
        if (email.isEmpty()) {
            tilEmail.error = "Ingrese un correo"
            error = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Ingrese un correo válido"
            error = true
        } else {
            tilEmail.error = null
        }
        
        // Validar password
        if (password.isEmpty()) {
            tilPassword.error = "Ingrese la contraseña"
            error = true
        } else {
            tilPassword.error = null
        }
        
        if (error) return
        
        CoroutineScope(Dispatchers.Main).launch {
            val usuario = withContext(Dispatchers.IO) {
                // Intentar validar con Firebase primero
                val firebaseUsuario = FirebaseUsuarioService().validarCredenciales(email, password)
                if (firebaseUsuario != null) {
                    return@withContext firebaseUsuario.nomUSU
                }
                
                // Si no está en Firebase, validar con SQLite local
                val dbHelper = AppDatabaseHelper(this@LoginActivity)
                val db = dbHelper.readableDatabase
                val cursor = db.query(
                    "usuarios",
                    arrayOf("nom_usu", "correo"),
                    "correo = ? AND clave = ?",
                    arrayOf(email, password),
                    null, null, null
                )
                var nombreUsuario: String? = null
                if (cursor.moveToFirst()) {
                    nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nom_usu"))
                }
                cursor.close()
                db.close()
                nombreUsuario
            }
            
            if (usuario != null) {
                sessionManager.login(email, usuario)
                Toast.makeText(this@LoginActivity, "Bienvenido $usuario", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

}