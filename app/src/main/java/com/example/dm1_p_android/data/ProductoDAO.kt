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
            put("id", producto.idProd)
            put("nom_pro", producto.nomProd)
            put("cod_pro", producto.codProd)
            put("stock", producto.stoProd)
            put("uni_medida", producto.uniMedida)
            put("precio", producto.preProd)
            put("fec_in", producto.fecInc)
            put("des_pro", producto.desProd)
            put("id_usu", producto.idUsuario)
            put("id_cat", producto.idCategoria)
            put("id_pro", producto.idProveedor)
        }
        return db.insert("producto", null, valores)
    }
}