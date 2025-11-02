package com.example.dm1_p_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.R
import com.example.dm1_p_android.entity.Categoria

class CategoriaAdapter(
    private val onEditar: (Categoria) -> Unit,
    private val onEliminar: (Categoria) -> Unit
) : ListAdapter<Categoria, CategoriaAdapter.CategoriaViewHolder>(CategoriaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view, onEditar, onEliminar)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoriaViewHolder(
        itemView: View,
        private val onEditar: (Categoria) -> Unit,
        private val onEliminar: (Categoria) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvIconoCategoria: TextView = itemView.findViewById(R.id.tvIconoCategoria)
        private val tvNombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)
        private val tvIdCategoria: TextView = itemView.findViewById(R.id.tvIdCategoria)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditarCategoria)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarCategoria)

        fun bind(categoria: Categoria) {
            tvNombreCategoria.text = categoria.nomCat
            tvIdCategoria.text = "ID: ${categoria.idCat}"

            // Emoji según el nombre (opcional)
            tvIconoCategoria.text = obtenerIcono(categoria.nomCat)

            btnEditar.setOnClickListener { onEditar(categoria) }
            btnEliminar.setOnClickListener { onEliminar(categoria) }
        }

        private fun obtenerIcono(nombre: String): String {
            return when {
                nombre.contains("electr", ignoreCase = true) -> "⚡"
                nombre.contains("aliment", ignoreCase = true) -> "🍎"
                nombre.contains("ropa", ignoreCase = true) -> "👕"
                nombre.contains("hogar", ignoreCase = true) -> "🏠"
                nombre.contains("deporte", ignoreCase = true) -> "⚽"
                else -> "📦"
            }
        }
    }

    class CategoriaDiffCallback : DiffUtil.ItemCallback<Categoria>() {
        override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
            return oldItem.idCat == newItem.idCat
        }

        override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
            return oldItem == newItem
        }
    }
}