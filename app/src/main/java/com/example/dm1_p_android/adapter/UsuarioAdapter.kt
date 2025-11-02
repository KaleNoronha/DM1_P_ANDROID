package com.example.dm1_p_android.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.R
import com.example.dm1_p_android.entity.Usuario

class UsuarioAdapter(
    private val onVerDetalle: (Usuario) -> Unit,
    private val onEditar: (Usuario) -> Unit,
    private val onEliminar: (Usuario) -> Unit
) : ListAdapter<Usuario, UsuarioAdapter.UsuarioViewHolder>(UsuarioDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view, onVerDetalle, onEditar, onEliminar)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UsuarioViewHolder(
        itemView: View,
        private val onVerDetalle: (Usuario) -> Unit,
        private val onEditar: (Usuario) -> Unit,
        private val onEliminar: (Usuario) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        private val tvNombreCompleto: TextView = itemView.findViewById(R.id.tvNombreCompleto)
        private val tvNombreUsuario: TextView = itemView.findViewById(R.id.tvNombreUsuario)
        private val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreo)
        private val tvCelular: TextView = itemView.findViewById(R.id.tvCelular)
        private val indicadorSexo: View = itemView.findViewById(R.id.indicadorSexo)
        private val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(usuario: Usuario) {
            // Nombre completo
            val nombreCompleto = "${usuario.nombres} ${usuario.apellidos}"
            tvNombreCompleto.text = nombreCompleto

            // Avatar con iniciales
            val iniciales = obtenerIniciales(usuario.nombres, usuario.apellidos)
            tvAvatar.text = iniciales

            // Nombre de usuario
            tvNombreUsuario.text = "@${usuario.nomUSU}"

            // Correo
            tvCorreo.text = usuario.correo

            // Celular
            tvCelular.text = usuario.celular

            // Indicador de sexo con colores
            when (usuario.sexo.uppercase()) {
                "M", "MASCULINO" -> {
                    indicadorSexo.setBackgroundColor(
                        itemView.context.getColor(R.color.primary_blue)
                    )
                }
                "F", "FEMENINO" -> {
                    indicadorSexo.setBackgroundColor(
                        Color.parseColor("#E91E63") // Rosa
                    )
                }
                else -> {
                    indicadorSexo.setBackgroundColor(
                        itemView.context.getColor(R.color.text_secondary)
                    )
                }
            }

            // Listeners
            btnVerDetalle.setOnClickListener { onVerDetalle(usuario) }
            btnEditar.setOnClickListener { onEditar(usuario) }
            btnEliminar.setOnClickListener { onEliminar(usuario) }

            // Click en toda la card
            itemView.setOnClickListener { onVerDetalle(usuario) }
        }

        private fun obtenerIniciales(nombres: String, apellidos: String): String {
            val inicialNombre = nombres.trim().firstOrNull()?.uppercase() ?: ""
            val inicialApellido = apellidos.trim().firstOrNull()?.uppercase() ?: ""
            return "$inicialNombre$inicialApellido".ifEmpty { "U" }
        }
    }

    class UsuarioDiffCallback : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem == newItem
        }
    }
}