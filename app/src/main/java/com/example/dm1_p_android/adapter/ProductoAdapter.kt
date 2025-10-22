package com.example.dm1_p_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.R
import com.example.dm1_p_android.entity.Producto

class ProductoAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreProducto: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvCodigoProducto: TextView = view.findViewById(R.id.tvCodigoProducto)
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidad)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombreProducto.text = producto.nomProd
        holder.tvCodigoProducto.text = producto.codProd
        holder.tvCantidad.text = producto.stoProd
        holder.tvPrecio.text = "$${producto.preProd}"
        holder.tvCategoria.text = producto.Categoria
    }

    override fun getItemCount() = productos.size
}