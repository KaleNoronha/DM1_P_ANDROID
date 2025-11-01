package com.example.dm1_p_android.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.AgregarProductoActivity
import com.example.dm1_p_android.R
import com.example.dm1_p_android.adapter.ProductoAdapter
import com.example.dm1_p_android.entity.ApiProduct
import com.example.dm1_p_android.network.ApiService
import com.example.dm1_p_android.network.FirebaseService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class InicioFragment : Fragment(R.layout.fragment_inicio) {

    private lateinit var etBuscar: EditText
    private lateinit var tvTotalProductos: TextView
    private lateinit var tvStockBajo: TextView
    private lateinit var tvValorTotal: TextView
    private lateinit var fabAgregarProducto: FloatingActionButton
    private lateinit var rvProductos: RecyclerView
    private lateinit var layoutEmptyState: LinearLayout
    
    private lateinit var productoAdapter: ProductoAdapter
    private var productosFiltrados = mutableListOf<ApiProduct>()
    private var todosLosProductos = mutableListOf<ApiProduct>()
    private val apiService = ApiService()
    private val firebaseService = FirebaseService()
    private var categoriaFiltro = "Todas"
    private var ordenamiento = "Nombre"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupRecyclerView()
        setupSearchFilter()
        setupFab()
        cargarProductosAPI()
    }
    
    override fun onResume() {
        super.onResume()
        actualizarUI()
    }
    
    private fun initViews(view: View) {
        etBuscar = view.findViewById(R.id.etBuscar)
        tvTotalProductos = view.findViewById(R.id.tvTotalProductos)
        tvStockBajo = view.findViewById(R.id.tvStockBajo)
        tvValorTotal = view.findViewById(R.id.tvValorTotal)
        rvProductos = view.findViewById(R.id.rvProductos)
        fabAgregarProducto = view.findViewById(R.id.fabAgregarProducto)
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState)
    }
    
    private fun setupRecyclerView() {
        productoAdapter = ProductoAdapter(
            productos = productosFiltrados,
            onEditClick = { producto -> editarProducto(producto) },
            onDeleteClick = { producto, position -> confirmarEliminar(producto, position) }
        )
        
        rvProductos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productoAdapter
        }
    }
    
    private fun setupSearchFilter() {
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarProductos(s.toString())
            }
        })
    }
    
    private fun setupFab() {
        fabAgregarProducto.setOnClickListener {
            mostrarOpcionesFiltro()
        }
    }
    
    private fun cargarProductosAPI() {
        lifecycleScope.launch {
            try {
                val productos = apiService.getProducts()
                todosLosProductos.clear()
                todosLosProductos.addAll(productos)
                
                // Sincronizar con Firebase
                sincronizarConFirebase(productos)
                
                actualizarUI()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun sincronizarConFirebase(productos: List<ApiProduct>) {
        lifecycleScope.launch {
            val resultado = firebaseService.subirProductos(productos)
            if (resultado) {
                Toast.makeText(context, "Productos sincronizados con Firebase", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun actualizarUI() {
        // Actualizar estadísticas
        tvTotalProductos.text = todosLosProductos.size.toString()
        tvStockBajo.text = todosLosProductos.count { it.rating.rate < 3.0 }.toString()
        
        val valorTotal = todosLosProductos.sumOf { it.price }
        tvValorTotal.text = "$${String.format("%.2f", valorTotal)}"
        
        // Actualizar lista
        filtrarProductos(etBuscar.text.toString())
        
        // Mostrar/ocultar estado vacío
        if (todosLosProductos.isEmpty()) {
            rvProductos.visibility = View.GONE
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            rvProductos.visibility = View.VISIBLE
            layoutEmptyState.visibility = View.GONE
        }
    }
    
    private fun filtrarProductos(query: String) {
        var productos = todosLosProductos.toList()
        
        // Filtrar por categoría
        if (categoriaFiltro != "Todas") {
            productos = productos.filter { it.category.equals(categoriaFiltro, ignoreCase = true) }
        }
        
        // Filtrar por búsqueda
        if (query.isNotEmpty()) {
            productos = productos.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
        
        // Ordenar
        productos = when (ordenamiento) {
            "Precio (Menor)" -> productos.sortedBy { it.price }
            "Precio (Mayor)" -> productos.sortedByDescending { it.price }
            "Rating" -> productos.sortedByDescending { it.rating.rate }
            else -> productos.sortedBy { it.title }
        }
        
        productosFiltrados.clear()
        productosFiltrados.addAll(productos)
        productoAdapter.notifyDataSetChanged()
    }
    
    private fun editarProducto(producto: ApiProduct) {
        Toast.makeText(context, "Ver detalles: ${producto.title}", Toast.LENGTH_SHORT).show()
    }
    
    private fun confirmarEliminar(producto: ApiProduct, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Está seguro que desea eliminar '${producto.title}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarProducto(producto, position)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun eliminarProducto(producto: ApiProduct, position: Int) {
        lifecycleScope.launch {
            val resultado = firebaseService.eliminarProducto(producto.id)
            if (resultado) {
                todosLosProductos.remove(producto)
                productoAdapter.removeItem(position)
                actualizarUI()
                Toast.makeText(context, "Producto eliminado de Firebase", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al eliminar de Firebase", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarOpcionesFiltro() {
        val opciones = arrayOf(
            "Filtrar por Categoría", 
            "Ordenar por", 
            "Sincronizar con Firebase",
            "Agregar Producto"
        )
        
        AlertDialog.Builder(requireContext())
            .setTitle("Opciones")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarFiltroCategoria()
                    1 -> mostrarOpcionesOrdenamiento()
                    2 -> sincronizarConFirebase(todosLosProductos)
                    3 -> cambioActivity(AgregarProductoActivity::class.java)
                }
            }
            .show()
    }
    
    private fun mostrarFiltroCategoria() {
        val categorias = listOf("Todas") + todosLosProductos.map { it.category }.distinct().sorted()
        val categoriasArray = categorias.toTypedArray()
        
        AlertDialog.Builder(requireContext())
            .setTitle("Filtrar por Categoría")
            .setSingleChoiceItems(categoriasArray, categorias.indexOf(categoriaFiltro)) { dialog, which ->
                categoriaFiltro = categorias[which]
                filtrarProductos(etBuscar.text.toString())
                dialog.dismiss()
                Toast.makeText(context, "Filtro: $categoriaFiltro", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun mostrarOpcionesOrdenamiento() {
        val opciones = arrayOf("Nombre", "Precio (Menor)", "Precio (Mayor)", "Rating")
        
        AlertDialog.Builder(requireContext())
            .setTitle("Ordenar por")
            .setSingleChoiceItems(opciones, opciones.indexOf(ordenamiento)) { dialog, which ->
                ordenamiento = opciones[which]
                filtrarProductos(etBuscar.text.toString())
                dialog.dismiss()
                Toast.makeText(context, "Ordenado por: $ordenamiento", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun cambioActivity(activityDestino: Class<out Activity>) {
        val intent = Intent(context, activityDestino)
        startActivity(intent)
    }
}