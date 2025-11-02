package com.example.dm1_p_android
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dm1_p_android.data.AppDatabaseHelper
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    private lateinit var tilNombreUsuario : TextInputLayout
    private lateinit var etNombreUsuario : TextInputEditText
    private lateinit var tilEmailRegistro : TextInputLayout
    private lateinit var etEmailRegistro : TextInputEditText
    private lateinit var tilPasswordRegistro : TextInputLayout
    private lateinit var etPasswordRegistro : TextInputEditText
    private lateinit var tilConfirmarPassword : TextInputLayout
    private lateinit var etConfirmarPassword : TextInputEditText
    private lateinit var mcbTCP : MaterialCheckBox
    private lateinit var btnRegistro : Button
    var tvIrALogin: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        tilNombreUsuario = findViewById(R.id.tilNombreUsuario)
        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        tilEmailRegistro = findViewById(R.id.tilEmailRegistro)
        etEmailRegistro = findViewById(R.id.etEmailRegistro)
        tilPasswordRegistro = findViewById(R.id.tilPasswordRegistro)
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro)
        tilConfirmarPassword = findViewById(R.id.tilConfirmarPassword)
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword)
        mcbTCP = findViewById(R.id.mcbTCP)
        btnRegistro = findViewById(R.id.btnRegistro)
        tvIrALogin = findViewById(R.id.tvIrALogin)

        btnRegistro.setOnClickListener {
            if (mcbTCP.isChecked) {
                registrarUsuario()
            } else {
                Toast.makeText(this, "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }

        // Mandar de regreso al Login
        tvIrALogin?.setOnClickListener {
            cambioActivity(LoginActivity::class.java)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun registrarUsuario() {
        var error = false
        if (etNombreUsuario.text.toString().trim().isEmpty()) {
            tilNombreUsuario.error = "Ingrese nombre de usuario"
            error = true
        } else {
            tilNombreUsuario.error = ""
        }
        if (etEmailRegistro.text.toString().trim().isEmpty()) {
            tilEmailRegistro.error = "Ingrese correo electrónico"
            error = true
        } else {
            tilEmailRegistro.error = ""
        }
        if (etPasswordRegistro.text.toString().trim().isEmpty()) {
            tilPasswordRegistro.error = "Ingrese contraseña"
            error = true
        } else {
            tilPasswordRegistro.error = ""
        }
        //COmmit
        val password = etPasswordRegistro.text.toString().trim()
        val confirmPassword = etConfirmarPassword.text.toString().trim()
        if (confirmPassword.isEmpty()) {
            tilConfirmarPassword.error = "Confirme la contraseña"
            error = true
        } else if (password != confirmPassword) {
            tilConfirmarPassword.error = "Las contraseñas no coinciden"
            error = true
        } else {
            tilConfirmarPassword.error = ""
        }
        if (error) return
        CoroutineScope(Dispatchers.Main).launch {
            val resultado = withContext(IO) {
                val dbHelper = AppDatabaseHelper(this@RegistroActivity)
                val db = dbHelper.writableDatabase
                val valores = ContentValues().apply {
                    put("nom_usu", etNombreUsuario.text.toString().trim())
                    put("correo", etEmailRegistro.text.toString().trim())
                    put("clave", etPasswordRegistro.text.toString().trim())
                }
                val id = db.insert("usuarios", null, valores)
                db.close()
                id
            }
            if (resultado > 0) {
                Toast.makeText(this@RegistroActivity, "Usuario correctamente registrado (#${String.format("%.2f", resultado)}", Toast.LENGTH_SHORT).show()
                cambioActivity(LoginActivity::class.java)
                finish()
            } else {
                Toast.makeText(this@RegistroActivity, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun cambioActivity(activityDestino: Class<out Activity>) {
        val intent = Intent(this, activityDestino)
        startActivity(intent)
    }
}