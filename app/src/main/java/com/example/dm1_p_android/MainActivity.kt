package com.example.dm1_p_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.adapter.ProductoAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var etBuscar : EditText

    private lateinit var tvTotalProductos : TextView

    private lateinit var tvStockBajo : TextView

    private lateinit var tvValorTotal : TextView
   private lateinit var fabAgregarProducto : FloatingActionButton

   private lateinit var rvProductos : RecyclerView

   private lateinit var layoutEmptyState : LinearLayout

   private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etBuscar = findViewById(R.id.etBuscar)
        tvTotalProductos = findViewById(R.id.tvTotalProductos)
        tvStockBajo = findViewById(R.id.tvStockBajo)
        tvValorTotal = findViewById(R.id.tvValorTotal)
        rvProductos = findViewById(R.id.rvProductos)
        fabAgregarProducto = findViewById(R.id.fabAgregarProducto)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun cambioActivity(activityDestino: Class<out Activity>) {
            val intent = Intent(this, activityDestino)
            startActivity(intent)
        }
        fabAgregarProducto.setOnClickListener {
            cambioActivity(AgregarProductoActivity::class.java)
        }
        
        actualizarUI()
    }
    
    override fun onResume() {
        super.onResume()
        actualizarUI()
    }
    
    private fun actualizarUI() {
        val productos = AgregarProductoActivity.listaProductos
        
        // Actualizar estadísticas
        tvTotalProductos.text = productos.size.toString()
        tvStockBajo.text = productos.count { it.stoProd.toIntOrNull() ?: 0 < 10 }.toString()
        tvValorTotal.text = "$${productos.sumOf { it.preProd * (it.stoProd.toIntOrNull() ?: 0) }}"
        
        // Configurar RecyclerView
        rvProductos.layoutManager = LinearLayoutManager(this)
        rvProductos.adapter = ProductoAdapter(productos)
    }
}