package com.example.dm1_p_android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dm1_p_android.entity.ApiProduct

class DetalleProductoActivity : AppCompatActivity() {
    
    private lateinit var tvTitulo: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var tvCategoria: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvRatingCount: TextView
    private lateinit var btnVolver: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)
        
        initViews()
        loadProductData()
        setupButtons()
    }
    
    private fun initViews() {
        tvTitulo = findViewById(R.id.tvTitulo)
        tvPrecio = findViewById(R.id.tvPrecio)
        tvCategoria = findViewById(R.id.tvCategoria)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvRating = findViewById(R.id.tvRating)
        tvRatingCount = findViewById(R.id.tvRatingCount)
        btnVolver = findViewById(R.id.btnVolver)
    }
    
    private fun loadProductData() {
        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        val title = intent.getStringExtra("PRODUCT_TITLE") ?: ""
        val price = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val category = intent.getStringExtra("PRODUCT_CATEGORY") ?: ""
        val description = intent.getStringExtra("PRODUCT_DESCRIPTION") ?: ""
        val rating = intent.getDoubleExtra("PRODUCT_RATING", 0.0)
        val ratingCount = intent.getIntExtra("PRODUCT_RATING_COUNT", 0)
        
        tvTitulo.text = title
        tvPrecio.text = "$${String.format("%.2f", price)}"
        tvCategoria.text = category.replaceFirstChar { it.uppercase() }
        tvDescripcion.text = description
        tvRating.text = "⭐ ${String.format("%.1f", rating)}"
        tvRatingCount.text = "($ratingCount reviews)"
    }
    
    private fun setupButtons() {
        btnVolver.setOnClickListener {
            finish()
        }
    }
}