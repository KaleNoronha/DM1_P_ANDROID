package com.example.dm1_p_android.entity

data class Producto (
    val idProd : Int = 0,
    val nomProd : String = "",
    val codProd : String = "",
    val stoProd : Int = 0,
    val uniMedida : String = "",
    val preProd : Double = 0.0,
    val fecInc : String = "",
    val desProd : String = "",
    val idUsuario : Int,
    val idCategoria : Int,
    val idProveedor : Int
)