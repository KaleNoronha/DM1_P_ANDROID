package com.example.dm1_p_android
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dm1_p_android.entity.Producto
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AgregarProductoActivity : AppCompatActivity() {
    companion object {
        val listaProductos = mutableListOf<Producto>()
    }
    private fun convertirUnidades(unidad: String): Double{
        return when(unidad){
            "Kilogramos" -> 1.0
            "Litros" -> 2.0
            "Unidades" -> 3.0
            "Metros" -> 4.0
            "Cajas" -> 5.0
            else -> 0.0
        }
    }

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

    var btnCancelar: Button?=null
    var btnGuardar : Button?=null



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
        tietCategoria = findViewById(R.id.tietCategoria)
        //variables de acciones para botones
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar=findViewById(R.id.btnCancelar)
        tilCategoria = findViewById(R.id.tilCategoria)


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

            // Crear y guardar el producto
            val producto = Producto(
                idProd = listaProductos.size + 1,
                nomProd = nombre,
                codProd = codigo,
                stoProd = cantidad,
                preProd = precio.toDouble(),
                uniMedida = convertirUnidades(unidadMedida),
                Categoria = categoria,
                desProd = descripcion
            )
            
            listaProductos.add(producto)
            Toast.makeText(this, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show()
            
            // Volver a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnGuardar?.setOnClickListener {
            validarCampos()
        }
        btnCancelar?.setOnClickListener {
            cancelar()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    fun cancelar(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}