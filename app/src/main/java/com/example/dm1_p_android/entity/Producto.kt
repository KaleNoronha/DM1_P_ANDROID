package com.example.dm1_p_android.entity

data class Producto (
    val idProd : Int,
    val nomProd : String,
    val codProd : String,
    val stoProd : Int,
    val uniMedida : String,
    val preProd : Double,
    val fecInc : String,
    val desProd : String,
    val idUsuario : Int,
    val idCategoria : Int,
    val idProveedor : Int
)