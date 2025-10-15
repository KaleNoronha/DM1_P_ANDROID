package com.example.dm1_p_android
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AgregarProductoActivity : AppCompatActivity() {
    private lateinit var tilNombreProd : TextInputLayout
    private lateinit var tietNombreProd : TextInputEditText
    private lateinit var tilCodigoProd : TextInputLayout
    private lateinit var tietCodigoProd : TextInputEditText
    private lateinit var tilCantidad : TextInputLayout
    private lateinit var tietCantidad : TextInputEditText
    private lateinit var tilPrecio : TextInputLayout
    private lateinit var tietPrecio : TextInputEditText
    private lateinit var tilUnidadMedida : TextInputLayout
    private lateinit var actvUnidadMedida: AutoCompleteTextView
    private lateinit var tilCategoria : TextInputLayout
    private lateinit var tietCategoria : TextInputEditText
    private lateinit var tilDescripcion : TextInputLayout
    private lateinit var tietDescripcion : TextInputEditText
    private lateinit var btnGuardar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_producto)

        tilNombreProd = findViewById(R.id.tilNombre)
        tietNombreProd = findViewById(R.id.tietNombre)
        tilCodigoProd = findViewById(R.id.tilCodigoProducto)
        tietCodigoProd = findViewById(R.id.tietCodigoProducto)
        tilCantidad = findViewById(R.id.tilCantidad)
        tietCantidad = findViewById(R.id.tietCantidad)
        tilPrecio = findViewById(R.id.tilPrecio)
        tietPrecio = findViewById(R.id.tietPrecio)
        tilUnidadMedida = findViewById(R.id.tilUnidadMedida)
        actvUnidadMedida = findViewById(R.id.actvUnidadMedida)
        tilDescripcion = findViewById(R.id.tilDescripcion)
        tietDescripcion = findViewById(R.id.tietDescripcion)
        btnGuardar = findViewById(R.id.btnGuardar)

        tilCategoria = findViewById(R.id.tilCategoria)
        tietCategoria = findViewById(R.id.tietCategoria)

        val unidades = listOf("Kilogramos", "Litros", "Unidades", "Metros", "Cajas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, unidades)
        actvUnidadMedida.setAdapter(adapter)
        fun validarCampos() {
            val nombre = tietNombreProd.text.toString().trim()
            val codigo = tietCodigoProd.text.toString().trim()
            val cantidad = tietCantidad.text.toString().trim()
            val precio = tietPrecio.text.toString().trim()
            val unidadMedida = actvUnidadMedida.text.toString().trim()
            val categoria = tietCategoria.text.toString().trim()
            val descripcion = tietDescripcion.text.toString().trim()

            var error = false

            if (nombre.isEmpty()) {
                tilNombreProd.error = "Ingrese el nombre del producto"
                error = true
            } else {
                tilNombreProd.error = null
            }

            if (codigo.isEmpty()) {
                tilCodigoProd.error = "Ingrese el código del producto"
                error = true
            } else {
                tilCodigoProd.error = null
            }

            if (cantidad.isEmpty()) {
                tilCantidad.error = "Ingrese la cantidad"
                error = true
            } else {
                tilCantidad.error = null
            }

            if (precio.isEmpty()) {
                tilPrecio.error = "Ingrese el precio"
                error = true
            } else {
                tilPrecio.error = null
            }

            if (unidadMedida.isEmpty()) {
                tilUnidadMedida.error = "Ingrese la unidad de medida"
                error = true
            } else {
                tilUnidadMedida.error = null
            }

            if (categoria.isEmpty()) {
                tilCategoria.error = "Ingrese la categoría"
                error = true
            } else {
                tilCategoria.error = null
            }

            if (descripcion.isEmpty()) {
                tilDescripcion.error = "Ingrese la descripción"
                error = true
            } else {
                tilDescripcion.error = null
            }

            if (error) return

            // Si llega aquí, todos los campos están llenos
            Toast.makeText(this, "Todos los campos son válidos", Toast.LENGTH_SHORT).show()

            // Usar logica luego de guardar
        }

        btnGuardar.setOnClickListener {
            validarCampos()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}