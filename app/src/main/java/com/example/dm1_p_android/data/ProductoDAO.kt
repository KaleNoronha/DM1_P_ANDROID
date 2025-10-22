package com.example.dm1_p_android.data

import android.content.ContentValues
import android.content.Context
import com.example.dm1_p_android.entity.Producto

class ProductoDAO(context: Context) {

    private val dbHelper = AppDatabaseHelper(context)

    //Funcion para agregar contenido a la tabla producto
    fun agregar(producto : Producto) : Long {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nom_pro", producto.nomProd)
            put("cod_pro", producto.codProd)
            put("stock", producto.stoProd)
            put("uni_medida", producto.uniMedida)
            put("precio", producto.preProd)
//            put("fec_in", producto.fecInc)
            put("des_pro", producto.desProd)
//            put("id_usu", producto.idUsuario)
            put("id_cat", producto.Categoria)
//            put("id_pro", producto.idProveedor)
        }
        return db.insert("producto", null, valores)
    }
    fun listar(idProducto: Producto): List<Producto> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Producto>()
        val cursor = db.rawQuery(
            "SELECT * FROM producto ORDER BY id DESC",
            arrayOf(idProducto.toString()))
        while (cursor.moveToNext()) {
            lista.add(
                Producto(
                    idProd      = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nomProd     = cursor.getString(cursor.getColumnIndexOrThrow("nom_pro")),
                    codProd     = cursor.getString(cursor.getColumnIndexOrThrow("cod_pro")),
                    stoProd     = cursor.getString(cursor.getColumnIndexOrThrow("stock")),
                    uniMedida   = cursor.getDouble(cursor.getColumnIndexOrThrow("uni_medida")),
                    preProd     = cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
//                    fecInc      = cursor.getString(cursor.getColumnIndexOrThrow("fec_in")),
                    desProd     = cursor.getString(cursor.getColumnIndexOrThrow("des_pro")),
//                    idUsuario   = cursor.getInt(cursor.getColumnIndexOrThrow("id_usu")),
                    Categoria = cursor.getString(cursor.getColumnIndexOrThrow("id_cat")),
//                    idProveedor = cursor.getInt(cursor.getColumnIndexOrThrow("id_pro"))
                )
            )
        }
        cursor.close()
        db.close()
        return lista
    }

}