package com.example.alramirezh.ladm_u3_p2_bdfirebasenotas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(bd: SQLiteDatabase) {
        bd.execSQL(
            "CREATE TABLE Nota( " +
                    "ID integer primary key autoincrement, " +
                    "TITULO varchar(200) ," +
                    "CONTENIDO varchar(300) ," +
                    "FECHA text" +
                    ")"
        )


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //se ejecuta siempre que, tras una actualizacion de la app, haya un cambio en la version


    }

}