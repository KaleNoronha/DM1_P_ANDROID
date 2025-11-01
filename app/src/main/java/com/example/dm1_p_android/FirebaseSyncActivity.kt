package com.example.dm1_p_android

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dm1_p_android.network.ApiService
import com.example.dm1_p_android.network.FirebaseService
import kotlinx.coroutines.launch

class FirebaseSyncActivity : AppCompatActivity() {
    
    private lateinit var tvEstado: TextView
    private lateinit var tvContador: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSincronizar: Button
    private lateinit var btnVolver: Button
    
    private val apiService = ApiService()
    private val firebaseService = FirebaseService()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_sync)
        
        initViews()
        setupButtons()
    }
    
    private fun initViews() {
        tvEstado = findViewById(R.id.tvEstado)
        tvContador = findViewById(R.id.tvContador)
        progressBar = findViewById(R.id.progressBar)
        btnSincronizar = findViewById(R.id.btnSincronizar)
        btnVolver = findViewById(R.id.btnVolver)
    }
    
    private fun setupButtons() {
        btnSincronizar.setOnClickListener {
            sincronizarProductos()
        }
        
        btnVolver.setOnClickListener {
            finish()
        }
    }
    
    private fun sincronizarProductos() {
        btnSincronizar.isEnabled = false
        progressBar.visibility = android.view.View.VISIBLE
        tvEstado.text = "Cargando productos de la API..."
        
        lifecycleScope.launch {
            try {
                // Obtener productos de la API
                val productos = apiService.getProducts()
                tvContador.text = "${productos.size} productos encontrados"
                
                // Subir a Firebase
                tvEstado.text = "Subiendo a Firebase..."
                val resultado = firebaseService.subirProductos(productos)
                
                if (resultado) {
                    tvEstado.text = "✓ Sincronización completada"
                    Toast.makeText(this@FirebaseSyncActivity, 
                        "Productos sincronizados exitosamente", 
                        Toast.LENGTH_LONG).show()
                } else {
                    tvEstado.text = "✗ Error en la sincronización"
                    Toast.makeText(this@FirebaseSyncActivity, 
                        "Error al sincronizar", 
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                tvEstado.text = "✗ Error: ${e.message}"
                Toast.makeText(this@FirebaseSyncActivity, 
                    "Error: ${e.message}", 
                    Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
                btnSincronizar.isEnabled = true
            }
        }
    }
}