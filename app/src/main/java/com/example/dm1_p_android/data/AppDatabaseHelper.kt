package com.example.dm1_p_android.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.text.trimIndent

class AppDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "Inventario.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
        CREATE TABLE usuarios(
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        nombres TEXT,
        apellidos TEXT,
        nom_usu TEXT UNIQUE,
        celular TEXT,
        correo TEXT UNIQUE,
        clave TEXT UNIQUE,
        sexo TEXT
        )
        """.trimIndent())

        db.execSQL("""
        CREATE TABLE categoria(
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        nom_cat TEXT
        )
        """.trimIndent())

        db.execSQL("""
        CREATE TABLE proveedor(
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        razon TEXT,
        correo TEXT,
        telefono TEXT,
        ciudad TEXT,
        direccion TEXT
        )
        """.trimIndent())

        db.execSQL("""
        CREATE TABLE producto(
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        nom_pro TEXT,
        cod_pro TEXT,
        stock INTEGER,
        uni_medida TEXT,
        precio REAL,
        fec_in TEXT,
        id_usu INTEGER,
        id_cat INTEGER,
        id_pro INTEGER,
        FOREIGN KEY (id_cat) REFERENCES categoria(id),
        FOREIGN KEY (id_usu) REFERENCES usuarios(id),
        FOREIGN KEY (id_pro) REFERENCES proveedor(id)
        )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS categoria")
        db.execSQL("DROP TABLE IF EXISTS proveedor")
        db.execSQL("DROP TABLE IF EXISTS producto")
        onCreate(db)
    }

}