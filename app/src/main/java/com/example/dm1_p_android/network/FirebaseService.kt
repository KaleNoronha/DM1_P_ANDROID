package com.example.dm1_p_android.network

import com.example.dm1_p_android.entity.ApiProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Servicio para gestionar la sincronización de productos con Firebase Realtime Database
 * 
 * Esta clase proporciona métodos para:
 * - Subir productos individuales o en lote a Firebase
 * - Obtener productos en tiempo real usando Flow
 * - Eliminar productos de la base de datos
 * 
 * @author Tu Nombre
 * @version 1.0
 */
class FirebaseService {
    
    // Instancia de Firebase Database
    private val database = FirebaseDatabase.getInstance()
    
    // Referencia al nodo "productos" en Firebase
    private val productosRef = database.getReference("productos")
    
    /**
     * Sube un producto individual a Firebase
     * 
     * @param producto El producto a subir
     * @return true si la operación fue exitosa, false en caso de error
     * 
     * Ejemplo de uso:
     * ```
     * val resultado = firebaseService.subirProducto(producto)
     * if (resultado) {
     *     // Producto subido exitosamente
     * }
     * ```
     */
    suspend fun subirProducto(producto: ApiProduct): Boolean {
        return try {
            // Usa el ID del producto como clave en Firebase
            productosRef.child(producto.id.toString()).setValue(producto).await()
            true
        } catch (e: Exception) {
            // Retorna false si hay algún error en la subida
            false
        }
    }
    
    /**
     * Sube múltiples productos a Firebase en una sola operación (batch)
     * 
     * Este método es más eficiente que subir productos uno por uno
     * ya que realiza una sola escritura en Firebase
     * 
     * @param productos Lista de productos a subir
     * @return true si todos los productos se subieron correctamente, false en caso de error
     * 
     * Ejemplo de uso:
     * ```
     * val productos = apiService.getProducts()
     * val resultado = firebaseService.subirProductos(productos)
     * ```
     */
    suspend fun subirProductos(productos: List<ApiProduct>): Boolean {
        return try {
            // Crea un mapa con todos los productos a actualizar
            val updates = mutableMapOf<String, Any>()
            productos.forEach { producto ->
                // Usa el ID como clave para cada producto
                updates[producto.id.toString()] = producto
            }
            // Actualiza todos los productos en una sola operación atómica
            productosRef.updateChildren(updates).await()
            true
        } catch (e: Exception) {
            // Retorna false si hay error en la sincronización
            false
        }
    }
    
    /**
     * Obtiene productos de Firebase en tiempo real usando Flow
     * 
     * Este método escucha cambios en Firebase y emite actualizaciones automáticamente
     * cuando los datos cambian en la base de datos
     * 
     * @return Flow que emite listas de productos cada vez que hay cambios
     * 
     * Ejemplo de uso:
     * ```
     * lifecycleScope.launch {
     *     firebaseService.obtenerProductos().collect { productos ->
     *         // Actualizar UI con los productos
     *     }
     * }
     * ```
     */
    fun obtenerProductos(): Flow<List<ApiProduct>> = callbackFlow {
        // Listener para escuchar cambios en Firebase
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Convierte los datos de Firebase a lista de productos
                val productos = mutableListOf<ApiProduct>()
                snapshot.children.forEach { child ->
                    // Deserializa cada hijo a un objeto ApiProduct
                    child.getValue(ApiProduct::class.java)?.let { productos.add(it) }
                }
                // Envía la lista actualizada al Flow
                trySend(productos)
            }
            
            override fun onCancelled(error: DatabaseError) {
                // Cierra el Flow si hay un error
                close(error.toException())
            }
        }
        
        // Registra el listener para escuchar cambios
        productosRef.addValueEventListener(listener)
        
        // Limpia el listener cuando el Flow se cancela
        awaitClose { productosRef.removeEventListener(listener) }
    }
    
    /**
     * Elimina un producto de Firebase por su ID
     * 
     * @param productoId ID del producto a eliminar
     * @return true si se eliminó correctamente, false en caso de error
     * 
     * Ejemplo de uso:
     * ```
     * val resultado = firebaseService.eliminarProducto(1)
     * if (resultado) {
     *     Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
     * }
     * ```
     */
    suspend fun eliminarProducto(productoId: Int): Boolean {
        return try {
            // Elimina el nodo del producto usando su ID
            productosRef.child(productoId.toString()).removeValue().await()
            true
        } catch (e: Exception) {
            // Retorna false si hay error al eliminar
            false
        }
    }
}