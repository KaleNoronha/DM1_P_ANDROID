package com.example.dm1_p_android.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.dm1_p_android.entity.Categoria

class CategoriaDAO(context: Context) {

    private val dbHelper = AppDatabaseHelper(context)

    // ==================== OPERACIONES BÁSICAS (CRUD) ====================

    /**
     * Lista todas las categorías ordenadas alfabéticamente
     */
    fun listarCategorias(): List<Categoria> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Categoria>()
        val cursor = db.rawQuery(
            "SELECT * FROM categoria ORDER BY nom_cat ASC",
            null
        )

        while (cursor.moveToNext()) {
            lista.add(cursorACategoria(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    /**
     * Agrega una nueva categoría a la base de datos
     * @return El ID de la categoría insertada o -1 si falla
     */
    fun agregarCategoria(categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nom_cat", categoria.nomCat)
        }
        val resultado = db.insert("categoria", null, valores)
        db.close()
        return resultado
    }

    /**
     * Actualiza los datos de una categoría existente
     * @return Número de filas actualizadas
     */
    fun actualizarCategoria(categoria: Categoria): Int {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nom_cat", categoria.nomCat)
        }
        val resultado = db.update(
            "categoria",
            valores,
            "id = ?",
            arrayOf(categoria.idCat.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Elimina una categoría de la base de datos
     * @return Número de filas eliminadas
     */
    fun eliminarCategoria(categoriaId: Int): Int {
        val db = dbHelper.writableDatabase
        val resultado = db.delete(
            "categoria",
            "id = ?",
            arrayOf(categoriaId.toString())
        )
        db.close()
        return resultado
    }

    /**
     * Elimina todas las categorías
     */
    fun eliminarTodasCategorias(): Int {
        val db = dbHelper.writableDatabase
        val resultado = db.delete("categoria", null, null)
        db.close()
        return resultado
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Obtiene una categoría por su ID
     */
    fun obtenerCategoriaPorId(id: Int): Categoria? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM categoria WHERE id = ?",
            arrayOf(id.toString())
        )

        val categoria = if (cursor.moveToFirst()) {
            cursorACategoria(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return categoria
    }

    /**
     * Obtiene una categoría por su nombre
     */
    fun obtenerCategoriaPorNombre(nombre: String): Categoria? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM categoria WHERE nom_cat = ? LIMIT 1",
            arrayOf(nombre)
        )

        val categoria = if (cursor.moveToFirst()) {
            cursorACategoria(cursor)
        } else {
            null
        }

        cursor.close()
        db.close()
        return categoria
    }

    // ==================== BÚSQUEDAS Y FILTROS ====================

    /**
     * Busca categorías por nombre
     */
    fun buscarCategorias(busqueda: String): List<Categoria> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Categoria>()
        val patron = "%$busqueda%"

        val cursor = db.rawQuery(
            "SELECT * FROM categoria WHERE nom_cat LIKE ? ORDER BY nom_cat ASC",
            arrayOf(patron)
        )

        while (cursor.moveToNext()) {
            lista.add(cursorACategoria(cursor))
        }

        cursor.close()
        db.close()
        return lista
    }

    // ==================== VALIDACIONES ====================

    /**
     * Verifica si existe una categoría con ese nombre
     */
    fun existeCategoria(nombre: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM categoria WHERE nom_cat = ?",
            arrayOf(nombre)
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    /**
     * Verifica si existe una categoría con ese nombre excluyendo un ID
     * Útil para validar en edición
     */
    fun existeCategoriaExcluyendo(nombre: String, categoriaId: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM categoria WHERE nom_cat = ? AND id != ?",
            arrayOf(nombre, categoriaId.toString())
        )

        val existe = cursor.moveToFirst() && cursor.getInt(0) > 0
        cursor.close()
        db.close()
        return existe
    }

    // ==================== CONTEOS Y ESTADÍSTICAS ====================

    /**
     * Cuenta el total de categorías registradas
     */
    fun contarCategorias(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM categoria", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        db.close()
        return count
    }

    /**
     * Cuenta cuántos productos tiene una categoría
     * (requiere que la tabla producto esté relacionada)
     */
    fun contarProductosPorCategoria(categoriaId: Int): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM producto WHERE id_cat = ?",
            arrayOf(categoriaId.toString())
        )
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        db.close()
        return count
    }

    // ==================== OPERACIONES AVANZADAS ====================

    /**
     * Inserta categorías de prueba iniciales
     */
    fun insertarCategoriasIniciales() {
        val db = dbHelper.writableDatabase

        val cursor = db.rawQuery("SELECT COUNT(*) FROM categoria", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()

        if (count == 0) {
            val categoriasIniciales = listOf(
                "Electrónica",
                "Ropa y Accesorios",
                "Alimentos y Bebidas",
                "Hogar y Jardín",
                "Deportes y Fitness",
                "Libros y Papelería",
                "Juguetes y Juegos",
                "Salud y Belleza",
                "Automotriz",
                "Mascotas"
            )

            categoriasIniciales.forEach { nombreCategoria ->
                val valores = ContentValues().apply {
                    put("nom_cat", nombreCategoria)
                }
                db.insert("categoria", null, valores)
            }
        }

        db.close()
    }

    /**
     * Obtiene categorías con la cantidad de productos
     * Retorna lista de pares (Categoria, cantidadProductos)
     */
    fun listarCategoriasConConteo(): List<Pair<Categoria, Int>> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Pair<Categoria, Int>>()

        val cursor = db.rawQuery(
            """
            SELECT c.id, c.nom_cat, COUNT(p.id) as total_productos
            FROM categoria c
            LEFT JOIN producto p ON c.id = p.id_cat
            GROUP BY c.id, c.nom_cat
            ORDER BY c.nom_cat ASC
            """,
            null
        )

        while (cursor.moveToNext()) {
            val categoria = Categoria(
                idCat = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nomCat = cursor.getString(cursor.getColumnIndexOrThrow("nom_cat")) ?: ""
            )
            val totalProductos = cursor.getInt(cursor.getColumnIndexOrThrow("total_productos"))
            lista.add(Pair(categoria, totalProductos))
        }

        cursor.close()
        db.close()
        return lista
    }

    // ==================== MÉTODO AUXILIAR ====================

    /**
     * Convierte un Cursor en un objeto Categoria
     */
    private fun cursorACategoria(cursor: Cursor): Categoria {
        return Categoria(
            idCat = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            nomCat = cursor.getString(cursor.getColumnIndexOrThrow("nom_cat")) ?: ""
        )
    }
}