package com.example.dm1_p_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.R
import com.example.dm1_p_android.adapter.CategoriaAdapter
import com.example.dm1_p_android.data.CategoriaDAO
import com.example.dm1_p_android.entity.Categoria
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class HistorialCategoriaFragment : Fragment(R.layout.fragment_historial_categoria) {

    private lateinit var categoriaDAO: CategoriaDAO
    private lateinit var adapter: CategoriaAdapter

    private lateinit var rvCategorias: RecyclerView
    private lateinit var layoutEmptyState: View
    private lateinit var fabAgregarCategoria: FloatingActionButton

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializarVistas(view)
        inicializarDAO()
        configurarRecyclerView()
        configurarFAB()
        cargarCategorias()
    }

    private fun inicializarVistas(view: View) {
        rvCategorias = view.findViewById(R.id.rvCategorias)
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState)
        fabAgregarCategoria = view.findViewById(R.id.fabAgregarCategoria)
    }

    private fun inicializarDAO() {
        categoriaDAO = CategoriaDAO(requireContext())
    }

    private fun configurarRecyclerView() {
        adapter = CategoriaAdapter(
            onEditar = { categoria ->
                editarCategoria(categoria)
            },
            onEliminar = { categoria ->
                confirmarEliminacion(categoria)
            }
        )

        rvCategorias.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistorialCategoriaFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun configurarFAB() {
        fabAgregarCategoria.setOnClickListener {
            mostrarDialogAgregarCategoria()
        }
    }

    private fun cargarCategorias() {
        scope.launch(Dispatchers.IO) {
            try {
                val categorias = categoriaDAO.listarCategorias()
                withContext(Dispatchers.Main) {
                    actualizarLista(categorias)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar categorías: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun actualizarLista(categorias: List<Categoria>) {
        adapter.submitList(categorias)

        if (categorias.isEmpty()) {
            rvCategorias.visibility = View.GONE
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            rvCategorias.visibility = View.VISIBLE
            layoutEmptyState.visibility = View.GONE
        }
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

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        btnGuardar.setOnClickListener {
            val nombreCategoria = etNuevaCategoria.text.toString().trim()

            if (nombreCategoria.isEmpty()) {
                tilNuevaCategoria.error = "Ingrese el nombre de la categoría"
                return@setOnClickListener
            }

            tilNuevaCategoria.error = null
            agregarCategoria(nombreCategoria)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun agregarCategoria(nombreCategoria: String) {
        scope.launch(Dispatchers.IO) {
            try {
                val categoria = Categoria(0, nombreCategoria)
                val id = categoriaDAO.agregarCategoria(categoria)

                withContext(Dispatchers.Main) {
                    if (id > 0) {
                        Toast.makeText(
                            requireContext(),
                            "Categoría '$nombreCategoria' agregada",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarCategorias()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al agregar la categoría",
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

    private fun editarCategoria(categoria: Categoria) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_agregar_categoria, null)

        val etNuevaCategoria = dialogView.findViewById<TextInputEditText>(R.id.etNuevaCategoria)
        val tilNuevaCategoria = dialogView.findViewById<TextInputLayout>(R.id.tilNuevaCategoria)
        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelarDialog)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarDialog)

        // Pre-llenar con el nombre actual
        etNuevaCategoria.setText(categoria.nomCat)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        btnGuardar.setOnClickListener {
            val nombreCategoria = etNuevaCategoria.text.toString().trim()

            if (nombreCategoria.isEmpty()) {
                tilNuevaCategoria.error = "Ingrese el nombre de la categoría"
                return@setOnClickListener
            }

            tilNuevaCategoria.error = null
            actualizarCategoria(categoria.copy(nomCat = nombreCategoria))
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun actualizarCategoria(categoria: Categoria) {
        scope.launch(Dispatchers.IO) {
            try {
                val resultado = categoriaDAO.actualizarCategoria(categoria)

                withContext(Dispatchers.Main) {
                    if (resultado > 0) {
                        Toast.makeText(
                            requireContext(),
                            "Categoría actualizada",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarCategorias()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al actualizar",
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

    private fun confirmarEliminacion(categoria: Categoria) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Categoría")
            .setMessage("¿Estás seguro de eliminar '${categoria.nomCat}'?\n\nEsta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarCategoria(categoria)
            }
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun eliminarCategoria(categoria: Categoria) {
        scope.launch(Dispatchers.IO) {
            try {
                val resultado = categoriaDAO.eliminarCategoria(categoria.idCat)

                withContext(Dispatchers.Main) {
                    if (resultado > 0) {
                        view?.let { v ->
                            Snackbar.make(
                                v,
                                "Categoría eliminada",
                                Snackbar.LENGTH_LONG
                            ).setAction("Deshacer") {
                                restaurarCategoria(categoria)
                            }.show()
                        }
                        cargarCategorias()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No se pudo eliminar la categoría",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al eliminar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun restaurarCategoria(categoria: Categoria) {
        scope.launch(Dispatchers.IO) {
            try {
                val categoriaRestaurada = Categoria(0, categoria.nomCat)
                val id = categoriaDAO.agregarCategoria(categoriaRestaurada)

                withContext(Dispatchers.Main) {
                    if (id > 0) {
                        Toast.makeText(
                            requireContext(),
                            "Categoría restaurada",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarCategorias()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al restaurar",
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

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }
}