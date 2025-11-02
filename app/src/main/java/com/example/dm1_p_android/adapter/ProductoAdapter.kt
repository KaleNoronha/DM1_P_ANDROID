package com.example.dm1_p_android.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dm1_p_android.DetalleProductoActivity
import com.example.dm1_p_android.R
import com.example.dm1_p_android.entity.ApiProduct

/**
 * Adapter para mostrar productos en un RecyclerView
 * 
 * Este adapter:
 * - Muestra información de productos (nombre, precio, rating, categoría)
 * - Maneja clicks en botones de ver detalles y eliminar
 * - Aplica colores dinámicos según el rating del producto
 * 
 * @param productos Lista mutable de productos a mostrar
 * @param onEditClick Callback cuando se hace click en "Ver"
 * @param onDeleteClick Callback cuando se hace click en "Eliminar"
 * 
 * @author Tu Nombre
 * @version 1.0
 */
class ProductoAdapter(
    private val productos: MutableList<ApiProduct>,
    private val onEditClick: (ApiProduct) -> Unit = {},
    private val onDeleteClick: (ApiProduct, Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    /**
     * ViewHolder que contiene las referencias a las vistas de cada item
     * 
     * Almacena las referencias para evitar llamadas repetidas a findViewById
     * mejorando el rendimiento del RecyclerView
     */
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreProducto: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvCodigoProducto: TextView = view.findViewById(R.id.tvCodigoProducto)
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidad)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
        val indicadorStock: View = view.findViewById(R.id.indicadorStock)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    /**
     * Crea una nueva instancia de ViewHolder
     * 
     * Este método se llama cuando RecyclerView necesita un nuevo ViewHolder
     * para representar un item
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    /**
     * Vincula los datos del producto con las vistas del ViewHolder
     * 
     * Este método se llama para mostrar los datos en la posición especificada
     * 
     * @param holder ViewHolder que debe actualizarse
     * @param position Posición del item en la lista
     */
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        
        // Asigna los datos del producto a las vistas
        holder.tvNombreProducto.text = producto.title
        holder.tvCodigoProducto.text = "ID: ${producto.id}"
        holder.tvCantidad.text = "★ ${producto.rating.rate} (${producto.rating.count})"
        holder.tvPrecio.text = "$${String.format("%.2f", producto.price)}"
        holder.tvCategoria.text = producto.category.replaceFirstChar { it.uppercase() }
        
        // Configura el color del indicador basado en el rating
        // Rojo: rating < 3.0, Naranja: rating < 4.0, Verde: rating >= 4.0
        val colorStock = when {
            producto.rating.rate < 3.0 -> R.color.stock_bajo
            producto.rating.rate < 4.0 -> R.color.stock_medio
            else -> R.color.stock_alto
        }
        holder.indicadorStock.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, colorStock)
        
        // Configura el botón "Ver" para abrir los detalles del producto
        holder.btnEditar.text = "Ver"
        holder.btnEditar.setOnClickListener {
            val context = holder.itemView.context
            // Crea un Intent con todos los datos del producto
            val intent = Intent(context, DetalleProductoActivity::class.java).apply {
                putExtra("PRODUCT_ID", producto.id)
                putExtra("PRODUCT_TITLE", producto.title)
                putExtra("PRODUCT_PRICE", producto.price)
                putExtra("PRODUCT_CATEGORY", producto.category)
                putExtra("PRODUCT_DESCRIPTION", producto.description)
                putExtra("PRODUCT_RATING", producto.rating.rate)
                putExtra("PRODUCT_RATING_COUNT", producto.rating.count)
            }
            context.startActivity(intent)
        }
        
        // Configura el botón "Eliminar" para invocar el callback
        holder.btnEliminar.setOnClickListener {
            onDeleteClick(producto, position)
        }
    }

    /**
     * Retorna el número total de items en la lista
     */
    override fun getItemCount() = productos.size
    
    /**
     * Elimina un item de la lista y notifica al RecyclerView
     * 
     * @param position Posición del item a eliminar
     */
    fun removeItem(position: Int) {
        productos.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, productos.size)
    }
    
    /**
     * Actualiza toda la lista de productos
     * 
     * @param newProductos Nueva lista de productos
     */
    fun updateData(newProductos: List<ApiProduct>) {
        productos.clear()
        productos.addAll(newProductos)
        notifyDataSetChanged()
    }
}