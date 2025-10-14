package com.example.dm1_p_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RegistroActivity : AppCompatActivity() {

    var tvIrALogin: TextView ?= null

    private lateinit var etNombreUsuario : TextInputEditText

    private lateinit var etEmailRegistro : TextInputEditText

    private lateinit var etPasswordRegistro : TextInputEditText

    private lateinit var etConfirmarPassword : TextInputEditText

    private lateinit var btnRegistro : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etEmailRegistro = findViewById(R.id.etEmailRegistro)
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro)
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword)
        btnRegistro = findViewById(R.id.btnRegistro)
        tvIrALogin = findViewById(R.id.tvIrALogin)

        tvIrALogin?.setOnClickListener {
            irALogin(LoginActivity::class.java)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun irALogin(activityDestino : Class<out Activity>) {
        val intent = Intent(this, activityDestino)
        startActivity(intent)
    }
}