package com.example.dm1_p_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dm1_p_android.entity.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin : Button
    private lateinit var tvIrARegistro: TextView
    private lateinit var tvOlvidastePassword : TextView
    private val listaUsuarios = mutableListOf(
        Usuario(1,"Carlos Daniel","Carrasco Siccha","ccarrasco","974144528","M","test@gmail.com","123a")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        btnLogin= findViewById(R.id.btnLogin)
        tvIrARegistro =findViewById(R.id.tvIrARegistro)
        tvOlvidastePassword  =findViewById(R.id.tvOlvidastePassword)

        fun cambioActivity(activityDestino: Class<out Activity>) {
            val intent = Intent(this, activityDestino)
            startActivity(intent)
        }
        fun validarCampos(){
            val email = etEmail.text.toString().trim()
            val password= etPassword.text.toString().trim()
            var error  :Boolean =false
            if (email.isEmpty()){
                tilEmail.error="Ingrese un correo"
                error=true
            }else {
                tilEmail.error=""
            }
            if (password.isEmpty()){
                tilPassword.error="Ingrese la contraseña"
                error=true
            }else {
                tilEmail.error=""
            }
            if (error) return
            else{
                var usuFounded : Usuario?= null
                for (u in listaUsuarios) {
                    if (u.correo == email  && u.clave == password) {
                        usuFounded = u
                        break
                    }
                }
                if (usuFounded !=null) {
                    Toast.makeText(this, "Bienvenido ${usuFounded.nombres}", Toast.LENGTH_SHORT).show()
                    cambioActivity(MainActivity::class.java)
                }else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()

                }
            }
        }
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
}