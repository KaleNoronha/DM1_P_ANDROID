package com.example.dm1_p_android.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dm1_p_android.AgregarProductoActivity
import com.example.dm1_p_android.R
import com.example.dm1_p_android.RegistroActivity
import com.example.dm1_p_android.data.CategoriaDAO
import com.example.dm1_p_android.entity.Categoria
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val intent = Intent(requireContext(), RegistroActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.ln_verUsuarios).setOnClickListener {
            navegarConAnimacion(HistorialUsuarioFragment())
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
            mostrarDialogAgregarCategoria()
        }
        findViewById<LinearLayout>(R.id.ln_verCategorias).setOnClickListener {
            navegarConAnimacion(HistorialCategoriaFragment())
        }

        findViewById<LinearLayout>(R.id.btnCerrarSesion).setOnClickListener {
            mostrarDialogCerrarSesion()
        }

    }
    private fun navegarConAnimacion(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun mostrarDialogAgregarCategoria() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_agregar_categoria, null)

        val etNuevaCategoria = dialogView.findViewById<TextInputEditText>(R.id.etNuevaCategoria)
        val tilNuevaCategoria = dialogView.findViewById<TextInputLayout>(R.id.tilNuevaCategoria)
        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelarDialog)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarDialog)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Botón Cancelar
        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        // Botón Guardar
        btnGuardar.setOnClickListener {
            val nombreCategoria = etNuevaCategoria.text.toString().trim()

            if (nombreCategoria.isEmpty()) {
                tilNuevaCategoria.error = "Ingrese el nombre de la categoría"
                return@setOnClickListener
            }

            tilNuevaCategoria.error = null
            guardarCategoria(nombreCategoria)
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun guardarCategoria(nombreCategoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categoriaDAO = CategoriaDAO(requireContext())
                val categoria = Categoria(0, nombreCategoria)
                val id = categoriaDAO.agregarCategoria(categoria)

                withContext(Dispatchers.Main) {
                    if (id > 0) {
                        Toast.makeText(
                            requireContext(),
                            "Categoría '$nombreCategoria' guardada",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al guardar la categoría",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
    private fun mostrarDialogCerrarSesion() {
        AlertDialog.Builder(requireContext())
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro que deseas cerrar sesión?")
            .setIcon(R.drawable.ic_logout)
            .setPositiveButton("Salir") { _, _ ->
                cerrarSesion()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun cerrarSesion() {
        // Limpiar SharedPreferences (sesión)
        val sharedPref = requireContext().getSharedPreferences("user_session", android.content.Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()

        // Mostrar mensaje
        Toast.makeText(requireContext(), "Sesión cerrada exitosamente", Toast.LENGTH_SHORT).show()

        // Navegar al Login y limpiar el back stack
        val intent = Intent(requireContext(), com.example.dm1_p_android.LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


}
