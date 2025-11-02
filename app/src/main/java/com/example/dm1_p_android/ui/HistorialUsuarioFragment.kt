package com.example.dm1_p_android.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.R
import com.example.dm1_p_android.adapter.UsuarioAdapter
import com.example.dm1_p_android.data.UsuarioDAO
import com.example.dm1_p_android.entity.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*

class HistorialUsuarioFragment : Fragment(R.layout.fragment_historial_usuario) {

    private lateinit var usuarioDAO: UsuarioDAO
    private lateinit var adapter: UsuarioAdapter

    private lateinit var rvUsuarios: RecyclerView
    private lateinit var etBuscar: TextInputEditText
    private lateinit var layoutEmptyState: View
    private lateinit var fabAgregarUsuario: FloatingActionButton

    private var searchJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializarVistas(view)
        inicializarDAO()
        configurarRecyclerView()
        configurarBusqueda()
        configurarFAB()
        cargarUsuarios()
    }

    private fun inicializarVistas(view: View) {
        rvUsuarios = view.findViewById(R.id.rvUsuarios)
        etBuscar = view.findViewById(R.id.etBuscar)
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState)
        fabAgregarUsuario = view.findViewById(R.id.fabAgregarUsuario)
    }

    private fun inicializarDAO() {
        usuarioDAO = UsuarioDAO(requireContext())
    }

    private fun configurarRecyclerView() {
        adapter = UsuarioAdapter(
            onVerDetalle = { usuario ->
                mostrarDetalleUsuario(usuario)
            },
            onEditar = { usuario ->
                editarUsuario(usuario)
            },
            onEliminar = { usuario ->
                confirmarEliminacion(usuario)
            }
        )

        rvUsuarios.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistorialUsuarioFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun configurarBusqueda() {
        etBuscar.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = scope.launch {
                delay(300) // Debounce de 300ms
                val busqueda = text.toString().trim()
                if (busqueda.isEmpty()) {
                    cargarUsuarios()
                } else {
                    buscarUsuarios(busqueda)
                }
            }
        }
    }

    private fun configurarFAB() {
        fabAgregarUsuario.setOnClickListener {
            agregarNuevoUsuario()
        }
    }

    private fun cargarUsuarios() {
        scope.launch(Dispatchers.IO) {
            try {
                val usuarios = usuarioDAO.listarUsuarios()
                withContext(Dispatchers.Main) {
                    actualizarLista(usuarios)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar usuarios: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun buscarUsuarios(busqueda: String) {
        scope.launch(Dispatchers.IO) {
            try {
                val usuarios = usuarioDAO.buscarUsuarios(busqueda)
                withContext(Dispatchers.Main) {
                    actualizarLista(usuarios)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error en la búsqueda: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun actualizarLista(usuarios: List<Usuario>) {
        adapter.submitList(usuarios)

        if (usuarios.isEmpty()) {
            rvUsuarios.visibility = View.GONE
            layoutEmptyState.visibility = View.VISIBLE
        } else {
            rvUsuarios.visibility = View.VISIBLE
            layoutEmptyState.visibility = View.GONE
        }
    }

    private fun mostrarDetalleUsuario(usuario: Usuario) {
        val sexoTexto = when (usuario.sexo.uppercase()) {
            "M", "MASCULINO" -> "Masculino"
            "F", "FEMENINO" -> "Femenino"
            else -> usuario.sexo
        }

        val mensaje = """
            ID: ${usuario.id}
            Nombre Completo: ${usuario.nombres} ${usuario.apellidos}
            Usuario: @${usuario.nomUSU}
            Correo: ${usuario.correo}
            Celular: ${usuario.celular}
            Sexo: $sexoTexto
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("Detalle del Usuario")
            .setMessage(mensaje)
            .setPositiveButton("Cerrar", null)
            .setNeutralButton("Editar") { _, _ ->
                editarUsuario(usuario)
            }
            .setNegativeButton("Eliminar") { _, _ ->
                confirmarEliminacion(usuario)
            }
            .show()
    }

    private fun editarUsuario(usuario: Usuario) {
        Toast.makeText(
            requireContext(),
            "Editar: ${usuario.nombres} ${usuario.apellidos}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun confirmarEliminacion(usuario: Usuario) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Usuario")
            .setMessage("¿Estás seguro de eliminar a ${usuario.nombres} ${usuario.apellidos}?\n\nEsta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarUsuario(usuario)
            }
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun eliminarUsuario(usuario: Usuario) {
        scope.launch(Dispatchers.IO) {
            try {
                val resultado = usuarioDAO.eliminarUsuario(usuario.id)
                withContext(Dispatchers.Main) {
                    if (resultado > 0) {
                        view?.let { v ->
                            Snackbar.make(
                                v,
                                "Usuario eliminado correctamente",
                                Snackbar.LENGTH_LONG
                            ).setAction("Deshacer") {
                                restaurarUsuario(usuario)
                            }.show()
                        }

                        cargarUsuarios()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No se pudo eliminar el usuario",
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

    private fun restaurarUsuario(usuario: Usuario) {
        scope.launch(Dispatchers.IO) {
            try {
                val usuarioRestaurado = Usuario(
                    id = 0,
                    nombres = usuario.nombres,
                    apellidos = usuario.apellidos,
                    nomUSU = usuario.nomUSU,
                    celular = usuario.celular,
                    sexo = usuario.sexo,
                    correo = usuario.correo,
                    clave = usuario.clave
                )

                val id = usuarioDAO.agregarUsuario(usuarioRestaurado)

                withContext(Dispatchers.Main) {
                    if (id > 0) {
                        Toast.makeText(
                            requireContext(),
                            "Usuario restaurado",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarUsuarios()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al restaurar usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al restaurar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun agregarNuevoUsuario() {
        Toast.makeText(
            requireContext(),
            "Agregar nuevo usuario",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        scope.cancel()
    }
}