package com.example.dm1_p_android.entity

data class Producto (
    val idProd : Int = 0,
    val nomProd : String = "",
    val codProd : String = "",
    val stoProd : String = "",
    val uniMedida : Double = 0.0,
    val preProd : Double = 0.0,
    //val fecInc : String = "",
    val desProd : String = "",
//    val idUsuario : Int,
    val Categoria : String="",
//    val idProveedor : Int
)