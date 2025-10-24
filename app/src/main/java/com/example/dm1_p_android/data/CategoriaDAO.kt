package com.example.dm1_p_android.data

import android.content.Context
import com.example.dm1_p_android.entity.Categoria

class CategoriaDAO(context: Context) {

    private val dbHelper = AppDatabaseHelper(context)

    fun listarCategorias(): List<Categoria> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Categoria>()
        val cursor = db.rawQuery(
            "SELECT * FROM categoria ORDER BY nom_cat ASC",
            null
        )
        
        while (cursor.moveToNext()) {
            lista.add(
                Categoria(
                    idCat = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nomCat = cursor.getString(cursor.getColumnIndexOrThrow("nom_cat"))
                )
            )
        }
        
        cursor.close()
        db.close()
        return lista
    }
    fun agregarCategoria(categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val valores = android.content.ContentValues().apply {
            put("nom_cat", categoria.nomCat)
        }
        return db.insert("categoria", null, valores)
    }

    fun obtenerCategoriaPorId(id: Int): Categoria? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM categoria WHERE id = ?",
            arrayOf(id.toString())
        )
        
        val categoria = if (cursor.moveToFirst()) {
            Categoria(
                idCat = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nomCat = cursor.getString(cursor.getColumnIndexOrThrow("nom_cat"))
            )
        } else {
            null
        }
        
        cursor.close()
        db.close()
        return categoria
    }

    fun insertarCategoriasIniciales() {
        val db = dbHelper.writableDatabase

        val cursor = db.rawQuery("SELECT COUNT(*) FROM categoria", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()

        if (count == 0) {
            val categoriasIniciales = listOf(
                "Electrónicos",
                "Ropa",
                "Alimentos",
                "Hogar y Jardín",
                "Deportes",
                "Libros",
                "Juguetes",
                "Salud y Belleza"
            )
            
            categoriasIniciales.forEach { nombreCategoria ->
                val valores = android.content.ContentValues().apply {
                    put("nom_cat", nombreCategoria)
                }
                db.insert("categoria", null, valores)
            }
        }
        
        db.close()
    }
}
