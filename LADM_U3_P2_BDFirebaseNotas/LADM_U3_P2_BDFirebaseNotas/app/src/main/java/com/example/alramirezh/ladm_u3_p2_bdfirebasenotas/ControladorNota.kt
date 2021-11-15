package com.example.alramirezh.ladm_u3_p2_bdfirebasenotas

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList

class ControladorNota(activity: AppCompatActivity)
{
    val activity = activity

    fun insertar(nota: Nota): Boolean {
        val tablaNota = BD(activity, "Nota", null, 1).writableDatabase
        val datos = ContentValues()
        datos.put("TITULO", nota.titulo)
        datos.put("CONTENIDO", nota.contenido)
        datos.put("FECHA", nota.fecha)


        if( tablaNota.insert("Nota",null , datos) == -1L){ return false }
        return true
    }


    fun filtrarPorBusqueda( subcadena : String ): ArrayList<Nota>
    {
        val tablaNota = BD( activity ,"Nota",null,1).readableDatabase
        val resultado = ArrayList<Nota>()
        val SQL = "SELECT * FROM Nota WHERE TITULO LIKE ?"
        val cursor = tablaNota.rawQuery( SQL , arrayOf( "%$subcadena%") )
        if(cursor.moveToFirst()){
            //si se posiciona en un primer renglon, SI se obtuvo resultado
            do {
                //leer la data
                var nota = Nota(
                    cursor.getString( cursor.getColumnIndex( "TITULO" ) ) ,
                    cursor.getString( cursor.getColumnIndex( "CONTENIDO" ) ) ,
                    cursor.getString( cursor.getColumnIndex( "FECHA" ) ) ,

                    )
                nota.id = cursor.getInt( cursor.getColumnIndex( "ID" ) )

                resultado.add(nota)
            }while(cursor.moveToNext())
        }else{
            //si moveToFirst regresa falso, entra al ELSE que significa que no hubo ningun resultado
        }
        return resultado
    }







    fun filtrarTodo( ): ArrayList<Nota>
    {
        val tablaNota = BD( activity ,"Nota",null,1).readableDatabase
        val resultado = ArrayList<Nota>()
        val SQL = "SELECT * FROM Nota"
        val cursor = tablaNota.rawQuery( SQL , null )
        if(cursor.moveToFirst()){
            //si se posiciona en un primer renglon, SI se obtuvo resultado
            do {
                //leer la data
                var nota = Nota(
                    cursor.getString( cursor.getColumnIndex( "TITULO" ) ) ,
                    cursor.getString( cursor.getColumnIndex( "CONTENIDO" ) ) ,
                    cursor.getString( cursor.getColumnIndex( "FECHA" ) ) ,

                    )
                nota.id = cursor.getInt( cursor.getColumnIndex( "ID" ) )

                resultado.add(nota)
            }while(cursor.moveToNext())
        }else{
            //si moveToFirst regresa falso, entra al ELSE que significa que no hubo ningun resultado
        }
        return resultado
    }





    fun eliminar (id : Int) : Boolean
    {
        val tablaNota = BD( activity ,"Nota",null,1).writableDatabase
        val resultado = tablaNota.delete("Nota","ID=?", arrayOf(id.toString()))
        if (resultado == 0)  return false
        return true
    }


    fun guardarCambios (nota : Nota)
    {
        val tablaNota = BD( activity ,"Nota",null,1).writableDatabase
        val SQL = "UPDATE Nota SET TITULO = ? , CONTENIDO = ? , FECHA = ? WHERE ID = ?"
        tablaNota.execSQL( SQL , arrayOf(
            nota.titulo ,
            nota.contenido ,
            nota.fecha ,
            nota.id
        ))
    }

    /*fun actualizarCliente ( idVehiculo : Int , idCliente: Int)
    {
        val tablaVehiculo = BD( activity ,"Vehiculo",null,1).writableDatabase
        val SQL = "UPDATE Vehiculo SET IDCLIENTE = ? WHERE IDVEHICULO = ?"
        tablaVehiculo.execSQL( SQL , arrayOf( idCliente , idVehiculo ))
    }



    fun convertirListaAString( lista: ArrayList<Int>) : String {
        var cad = ""
        lista.forEach {
            cad = cad + it.toString() + ","
        }
        return cad.substring( 0 , cad.length - 1 )
    }*/
}