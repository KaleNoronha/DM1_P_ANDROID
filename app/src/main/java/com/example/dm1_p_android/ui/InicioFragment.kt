package com.example.dm1_p_android.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.AgregarProductoActivity
import com.example.dm1_p_android.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InicioFragment : Fragment(R.layout.fragment_inicio) {

    private lateinit var etBuscar : EditText
    private lateinit var tvTotalProductos : TextView
    private lateinit var tvStockBajo : TextView
    private lateinit var tvValorTotal : TextView
    private lateinit var fabAgregarProducto : FloatingActionButton
    private lateinit var rvProductos : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        etBuscar = view.findViewById(R.id.etBuscar)
        tvTotalProductos = view.findViewById(R.id.tvTotalProductos)
        tvStockBajo = view.findViewById(R.id.tvStockBajo)
        tvValorTotal = view.findViewById(R.id.tvValorTotal)
        rvProductos = view.findViewById(R.id.rvProductos)
        fabAgregarProducto = view.findViewById(R.id.fabAgregarProducto)

        fabAgregarProducto.setOnClickListener {
            cambioActivity(AgregarProductoActivity::class.java)
        }
    }

    fun cambioActivity(activityDestino: Class<out Activity>) {
        val intent = Intent(context, activityDestino)
        startActivity(intent)
    }
}