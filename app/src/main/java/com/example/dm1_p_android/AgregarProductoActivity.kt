package com.example.dm1_p_android
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dm1_p_android.entity.Producto
import com.example.dm1_p_android.entity.Categoria
import com.example.dm1_p_android.data.CategoriaDAO
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
    private lateinit var actvCategoria: AutoCompleteTextView
    private lateinit var tilDescripcion : TextInputLayout
    private lateinit var tietDescripcion : TextInputEditText

    var btnCancelar: Button?=null
    var btnGuardar : Button?=null
    var btnAgregarCategoria: Button?=null



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
        tilCategoria = findViewById(R.id.tilCategoria)
        actvCategoria = findViewById(R.id.actvCategoria)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar=findViewById(R.id.btnCancelar)
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria)


        val unidades = listOf("Kilogramos", "Litros", "Unidades", "Metros", "Cajas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, unidades)
        actvUnidadMedida.setAdapter(adapter)

        cargarCategorias()
        fun validarCampos() {
            val nombre = tietNombreProd.text.toString().trim()
            val codigo = tietCodigoProd.text.toString().trim()
            val cantidad = tietCantidad.text.toString().trim()
            val precio = tietPrecio.text.toString().trim()
            val unidadMedida = actvUnidadMedida.text.toString().trim()
            val categoria = actvCategoria.text.toString().trim()
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

            val categoriaDAO = CategoriaDAO(this)
            val categoriaSeleccionada = categoriaDAO.listarCategorias().find { it.nomCat == categoria }
            val idCategoria = categoriaSeleccionada?.idCat ?: 1 // Usar ID 1 como fallback

            val producto = Producto(
                idProd = listaProductos.size + 1,
                nomProd = nombre,
                codProd = codigo,
                stoProd = cantidad.toInt(),
                uniMedida = unidadMedida,
                preProd = precio.toDouble(),
                fecInc = "",
                desProd = descripcion,
                idUsuario = 1,
                idCategoria = idCategoria,
                idProveedor = 1
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
        btnAgregarCategoria?.setOnClickListener {
            mostrarDialogoAgregarCategoria()
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

    private fun cargarCategorias() {
        try {
            val categoriaDAO = CategoriaDAO(this)

            categoriaDAO.insertarCategoriasIniciales()
            
            val categorias = categoriaDAO.listarCategorias()
            
            if (categorias.isNotEmpty()) {
                val nombresCategorias = categorias.map { it.nomCat }
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresCategorias)
                actvCategoria.setAdapter(adapter)
            } else {
                Toast.makeText(this, "No hay categorías registradas en la base de datos", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar categorías: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoAgregarCategoria() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_agregar_categoria, null)
        
        val tilNuevaCategoria = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilNuevaCategoria)
        val tietNuevaCategoria = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNuevaCategoria)
        val btnGuardarDialog = dialogView.findViewById<Button>(R.id.btnGuardarDialog)
        val btnCancelarDialog = dialogView.findViewById<Button>(R.id.btnCancelarDialog)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnGuardarDialog.setOnClickListener {
            val nombreCategoria = tietNuevaCategoria.text.toString().trim()
            
            if (nombreCategoria.isEmpty()) {
                tilNuevaCategoria.error = "Ingrese el nombre de la categoría"
                return@setOnClickListener
            }

            val categoriaDAO = CategoriaDAO(this)
            val categoriasExistentes = categoriaDAO.listarCategorias()
            
            if (categoriasExistentes.any { it.nomCat.equals(nombreCategoria, ignoreCase = true) }) {
                tilNuevaCategoria.error = "Esta categoría ya existe"
                return@setOnClickListener
            }

            val nuevaCategoria = Categoria(idCat = 0, nomCat = nombreCategoria)
            val resultado = categoriaDAO.agregarCategoria(nuevaCategoria)
            
            if (resultado > 0) {
                Toast.makeText(this, "Categoría agregada exitosamente", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                cargarCategorias()
                actvCategoria.setText(nombreCategoria, false)
            } else {
                Toast.makeText(this, "Error al agregar la categoría", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelarDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}