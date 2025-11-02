package com.example.dm1_p_android.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dm1_p_android.AgregarProductoActivity
import com.example.dm1_p_android.R

class OpcionesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // args? agrégalos si los necesitas
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_opciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {

        fun toggle(menu: View, chevron: ImageView) {
            val showing = menu.visibility == View.VISIBLE
            menu.visibility = if (showing) View.GONE else View.VISIBLE
            chevron.animate().rotation(if (showing) 0f else 180f).setDuration(150).start()
        }

        // ===== USUARIOS =====
        findViewById<LinearLayout>(R.id.btnMembers).setOnClickListener {
            toggle(findViewById(R.id.subMenu), findViewById(R.id.ivChevron))
        }
        findViewById<LinearLayout>(R.id.ln_registrarUsuarios).setOnClickListener {
            Toast.makeText(requireContext(), "Añadir usuarios", Toast.LENGTH_SHORT).show()
            // TODO: navegar a pantalla de alta de usuarios
        }
        findViewById<LinearLayout>(R.id.ln_verUsuarios).setOnClickListener {
            Toast.makeText(requireContext(), "Ver usuarios", Toast.LENGTH_SHORT).show()
            // TODO: navegar a listado de usuarios
        }

        // ===== PRODUCTOS =====
        findViewById<LinearLayout>(R.id.btnProductos).setOnClickListener {
            toggle(findViewById(R.id.subMenuProductos), findViewById(R.id.ivChevronProductos))
        }
        findViewById<LinearLayout>(R.id.ln_registrarProducto).setOnClickListener {
            val intent = Intent(requireContext(), AgregarProductoActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.ln_verProductos).setOnClickListener {
            // TODO: navegar a listado dze productos
        }

        // ===== CATEGORÍAS =====
        findViewById<LinearLayout>(R.id.btnCategorias).setOnClickListener {
            toggle(findViewById(R.id.subMenuCategorias), findViewById(R.id.ivChevronCategorias))
        }
        findViewById<LinearLayout>(R.id.ln_registrarCategoria).setOnClickListener {
            // TODO: navegar a alta de categoría
        }
        findViewById<LinearLayout>(R.id.ln_verCategorias).setOnClickListener {
            // TODO: navegar a listado de categorías
        }

        // ===== CERRAR SESIÓN =====
        findViewById<LinearLayout>(R.id.btnCerrarSesion).setOnClickListener {
            // TODO: limpiar sesión y navegar a Login
            Toast.makeText(requireContext(), "Cerrando sesión…", Toast.LENGTH_SHORT).show()
        }

    }

}
