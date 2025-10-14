package com.example.dm1_p_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

   private lateinit var fabAgregarProducto : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fabAgregarProducto = findViewById(R.id.fabAgregarProducto)

        fun cambioActivity(activityDestino: Class<out Activity>) {
            val intent = Intent(this, activityDestino)
            startActivity(intent)
        }
        fabAgregarProducto.setOnClickListener {
            cambioActivity(AgregarProductoActivity::class.java)
        }
    }
}