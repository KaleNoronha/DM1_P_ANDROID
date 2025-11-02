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
import com.example.dm1_p_android.LoginActivity
import com.example.dm1_p_android.R
import com.example.dm1_p_android.utils.SessionManager

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun toggle(menu: View, chevron: ImageView) {
            val showing = menu.visibility == View.VISIBLE
            menu.visibility = if (showing) View.GONE else View.VISIBLE
            chevron.animate().rotation(if (showing) 0f else 180f).setDuration(150).start()
        }

        // ===== USUARIOS =====
        view.findViewById<LinearLayout>(R.id.btnMembers)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenu)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevron)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarUsuarios)?.setOnClickListener {
            Toast.makeText(requireContext(), "Añadir usuarios", Toast.LENGTH_SHORT).show()
            // TODO: navegar a pantalla de alta de usuarios
        }
        view.findViewById<LinearLayout>(R.id.ln_verUsuarios)?.setOnClickListener {
            Toast.makeText(requireContext(), "Ver usuarios", Toast.LENGTH_SHORT).show()
            // TODO: navegar a listado de usuarios
        }

        // ===== PRODUCTOS =====
        view.findViewById<LinearLayout>(R.id.btnProductos)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuProductos)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronProductos)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarProducto)?.setOnClickListener {
            val intent = Intent(requireContext(), AgregarProductoActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<LinearLayout>(R.id.ln_verProductos)?.setOnClickListener {
            // TODO: navegar a listado de productos
        }

        // ===== CATEGORÍAS =====
        view.findViewById<LinearLayout>(R.id.btnCategorias)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuCategorias)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronCategorias)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarCategoria)?.setOnClickListener {
            // TODO: navegar a alta de categoría
        }
        view.findViewById<LinearLayout>(R.id.ln_verCategorias)?.setOnClickListener {
            // TODO: navegar a listado de categorías
        }

        // ===== PROVEEDORES =====
        view.findViewById<LinearLayout>(R.id.btnProveedores)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuProveedores)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronProveedores)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarProveedor)?.setOnClickListener {
            // TODO: navegar a alta de proveedor
        }
        view.findViewById<LinearLayout>(R.id.ln_verProveedores)?.setOnClickListener {
            // TODO: navegar a listado de proveedores
        }

        // ===== CLIENTES =====
        view.findViewById<LinearLayout>(R.id.btnClientes)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuClientes)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronClientes)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarCliente)?.setOnClickListener {
            // TODO: navegar a alta de cliente
        }
        view.findViewById<LinearLayout>(R.id.ln_verClientes)?.setOnClickListener {
            // TODO: navegar a listado de clientes
        }

        // ===== COMPRAS =====
        view.findViewById<LinearLayout>(R.id.btnCompras)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuCompras)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronCompras)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarCompra)?.setOnClickListener {
            // TODO: navegar a registro de compra
        }
        view.findViewById<LinearLayout>(R.id.ln_verCompras)?.setOnClickListener {
            // TODO: navegar a listado de compras
        }

        // ===== VENTAS =====
        view.findViewById<LinearLayout>(R.id.btnVentas)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuVentas)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronVentas)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_registrarVenta)?.setOnClickListener {
            // TODO: navegar a registro de venta
        }
        view.findViewById<LinearLayout>(R.id.ln_verVentas)?.setOnClickListener {
            // TODO: navegar a listado de ventas
        }

        // ===== REPORTES =====
        view.findViewById<LinearLayout>(R.id.btnReportes)?.setOnClickListener {
            view.findViewById<View>(R.id.subMenuReportes)?.let { menu ->
                view.findViewById<ImageView>(R.id.ivChevronReportes)?.let { chevron ->
                    toggle(menu, chevron)
                }
            }
        }
        view.findViewById<LinearLayout>(R.id.ln_repVentas)?.setOnClickListener {
            // TODO: navegar a reporte de ventas
        }
        view.findViewById<LinearLayout>(R.id.ln_repCompras)?.setOnClickListener {
            // TODO: navegar a reporte de compras
        }
        view.findViewById<LinearLayout>(R.id.ln_repStockMin)?.setOnClickListener {
            // TODO: navegar a reporte de stock mínimo
        }

        // ===== CERRAR SESIÓN =====
        view.findViewById<LinearLayout>(R.id.btnCerrarSesion)?.setOnClickListener {
            SessionManager(requireContext()).logout()
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

}
