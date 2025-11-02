package com.example.dm1_p_android.network

import com.example.dm1_p_android.entity.ApiProduct
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Servicio para consumir la API de FakeStore
 * 
 * Esta clase se encarga de:
 * - Realizar peticiones HTTP a la API externa
 * - Parsear la respuesta JSON manualmente
 * - Convertir los datos a objetos ApiProduct
 * 
 * URL de la API: https://fakestoreapi.com/products
 * 
 * @author Tu Nombre
 * @version 1.0
 */
class ApiService {
    
    /**
     * Obtiene la lista de productos desde la API de FakeStore
     * 
     * Realiza una petición GET a https://fakestoreapi.com/products
     * y retorna una lista de productos parseados
     * 
     * @return Lista de productos obtenidos de la API, lista vacía si hay error
     * 
     * Ejemplo de uso:
     * ```
     * lifecycleScope.launch {
     *     val productos = apiService.getProducts()
     *     // Usar productos
     * }
     * ```
     */
    suspend fun getProducts(): List<ApiProduct> = withContext(Dispatchers.IO) {
        try {
            // Crea la conexión HTTP a la API
            val url = URL("https://fakestoreapi.com/products")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            
            // Verifica el código de respuesta
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lee la respuesta JSON
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()
                
                // Parsea el JSON y retorna la lista de productos
                parseProducts(response)
            } else {
                // Retorna lista vacía si el código no es 200
                emptyList()
            }
        } catch (e: Exception) {
            // Retorna lista vacía en caso de error de red o parsing
            emptyList()
        }
    }
    
    /**
     * Parsea el JSON de respuesta y convierte a lista de ApiProduct
     * 
     * Este método realiza parsing manual del JSON sin usar librerías externas
     * como Gson o Moshi
     * 
     * @param jsonString String JSON obtenido de la API
     * @return Lista de productos parseados
     * 
     * Estructura esperada del JSON:
     * ```json
     * [
     *   {
     *     "id": 1,
     *     "title": "Product Name",
     *     "price": 109.95,
     *     "description": "Description",
     *     "category": "electronics",
     *     "image": "https://...",
     *     "rating": {
     *       "rate": 3.9,
     *       "count": 120
     *     }
     *   }
     * ]
     * ```
     */
    private fun parseProducts(jsonString: String): List<ApiProduct> {
        val products = mutableListOf<ApiProduct>()
        try {
            // Convierte el string a JSONArray
            val jsonArray = JSONArray(jsonString)
            
            // Itera sobre cada producto en el array
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                // Extrae el objeto rating anidado
                val ratingObject = jsonObject.getJSONObject("rating")
                
                // Crea el objeto ApiProduct con todos los campos
                val product = ApiProduct(
                    id = jsonObject.getInt("id"),
                    title = jsonObject.getString("title"),
                    price = jsonObject.getDouble("price"),
                    description = jsonObject.getString("description"),
                    category = jsonObject.getString("category"),
                    image = jsonObject.getString("image"),
                    rating = com.example.dm1_p_android.entity.Rating(
                        rate = ratingObject.getDouble("rate"),
                        count = ratingObject.getInt("count")
                    )
                )
                products.add(product)
            }
        } catch (e: Exception) {
            // Si hay error en el parsing, retorna la lista parcial o vacía
        }
        return products
    }
}