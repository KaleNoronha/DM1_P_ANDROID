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
            // TODO: navegar a listado de productos
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

        // ===== PROVEEDORES =====
        findViewById<LinearLayout>(R.id.btnProveedores).setOnClickListener {
            toggle(findViewById(R.id.subMenuProveedores), findViewById(R.id.ivChevronProveedores))
        }
        findViewById<LinearLayout>(R.id.ln_registrarProveedor).setOnClickListener {
            // TODO: navegar a alta de proveedor
        }
        findViewById<LinearLayout>(R.id.ln_verProveedores).setOnClickListener {
            // TODO: navegar a listado de proveedores
        }

        // ===== CLIENTES =====
        findViewById<LinearLayout>(R.id.btnClientes).setOnClickListener {
            toggle(findViewById(R.id.subMenuClientes), findViewById(R.id.ivChevronClientes))
        }
        findViewById<LinearLayout>(R.id.ln_registrarCliente).setOnClickListener {
            // TODO: navegar a alta de cliente
        }
        findViewById<LinearLayout>(R.id.ln_verClientes).setOnClickListener {
            // TODO: navegar a listado de clientes
        }

        // ===== COMPRAS =====
        findViewById<LinearLayout>(R.id.btnCompras).setOnClickListener {
            toggle(findViewById(R.id.subMenuCompras), findViewById(R.id.ivChevronCompras))
        }
        findViewById<LinearLayout>(R.id.ln_registrarCompra).setOnClickListener {
            // TODO: navegar a registro de compra
        }
        findViewById<LinearLayout>(R.id.ln_verCompras).setOnClickListener {
            // TODO: navegar a listado de compras
        }

        // ===== VENTAS =====
        findViewById<LinearLayout>(R.id.btnVentas).setOnClickListener {
            toggle(findViewById(R.id.subMenuVentas), findViewById(R.id.ivChevronVentas))
        }
        findViewById<LinearLayout>(R.id.ln_registrarVenta).setOnClickListener {
            // TODO: navegar a registro de venta
        }
        findViewById<LinearLayout>(R.id.ln_verVentas).setOnClickListener {
            // TODO: navegar a listado de ventas
        }

        // ===== REPORTES =====
        findViewById<LinearLayout>(R.id.btnReportes).setOnClickListener {
            toggle(findViewById(R.id.subMenuReportes), findViewById(R.id.ivChevronReportes))
        }
        findViewById<LinearLayout>(R.id.ln_repVentas).setOnClickListener {
            // TODO: navegar a reporte de ventas
        }
        findViewById<LinearLayout>(R.id.ln_repCompras).setOnClickListener {
            // TODO: navegar a reporte de compras
        }
        findViewById<LinearLayout>(R.id.ln_repStockMin).setOnClickListener {
            // TODO: navegar a reporte de stock mínimo
        }

        // ===== CERRAR SESIÓN =====
        findViewById<LinearLayout>(R.id.btnCerrarSesion).setOnClickListener {
            // TODO: limpiar sesión y navegar a Login
            Toast.makeText(requireContext(), "Cerrando sesión…", Toast.LENGTH_SHORT).show()
        }

    }

}
