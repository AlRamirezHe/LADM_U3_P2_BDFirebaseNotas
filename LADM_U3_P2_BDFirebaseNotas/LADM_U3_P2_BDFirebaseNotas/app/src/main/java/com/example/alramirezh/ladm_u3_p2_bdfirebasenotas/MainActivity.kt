package com.example.alramirezh.ladm_u3_p2_bdfirebasenotas

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val baseDatos = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //=============================================
        //  ESTABLECER DATOS INICIALES
        //=============================================
        contenedorRNotas.removeAllViews()
        val listaNotas = ControladorNota( this ).filtrarTodo()
        listaNotas.forEach {
            addNota( it )
        }


        //=============================================
        //  EVENTOS
        //=============================================
        /*botonRegresar.setOnClickListener {
            finish()
        }*/


        botonBuscar.setOnClickListener {
            contenedorRNotas.removeAllViews()
            if( campoBuscar.text.isEmpty() ) {
                // DEVOLVER todoo
                val listaNotas = ControladorNota( this ).filtrarTodo()
                listaNotas.forEach {
                    addNota( it )
                }
                ToastPersonalizado(this, "Busqueda Realizada").show()
            }
            else {
                // DEVOLVER COINCIDENCIAS
                val listaNotas = ControladorNota( this ).filtrarPorBusqueda( campoBuscar.text.toString() )
                listaNotas.forEach {
                    addNota( it )
                }
                ToastPersonalizado(this, "Busqueda Realizada").show()
            }
        }

        botonAgregar.setOnClickListener {
            val lanzar = Intent(this@MainActivity , PantallaNota::class.java)
            lanzar.putExtra( "esNuevoRegistro" , true )
            startActivity(lanzar)
            finish()


        }

        botonSubir.setOnClickListener {

            var bandera = true
           baseDatos.collection("nota").whereGreaterThan("ID",0).get().addOnSuccessListener { documentos ->


                if(bandera) {

                    bandera = false

                    var i = documentos.size()
                    var c = 0
                    if (i == 0) {
                        insertarnube()
                    } else {


                        for (doc in documentos!!) {



                            //val id = doc.getString("ID")
                            val titulo = doc.getString("TITULO")
                            baseDatos.collection("nota").document(doc.id).delete()
                                .addOnSuccessListener {
                                    c += 1
                                    if (c == i) {
                                       insertarnube()

                                    }
                                    ToastPersonalizado(this, "La nota"/*id*/ + " con el titulo " + titulo + " se ha eliminado de la nube").show()

                                }
                                .addOnFailureListener {
                                    ToastPersonalizado(this, "No se puedo eliminar").show()
                                }
                        }
                    }
                }
            }
                .addOnFailureListener {
                    ToastPersonalizado(this,"Fallido").show()
                }


        }


        //========================================
        // BOTONES DE AYUDA
        //========================================
        ayuda_campoBuscar.setOnClickListener {
            ToastPersonalizado( this , "Titulo a buscar" ).show()
        }

    }



    fun addNota(nota : Nota)
    {
        // LINEAR LAYOUT PRINCIPAL
        val contenedorMain = LinearLayout(this)
        contenedorRNotas.addView(contenedorMain)

        val p1 = contenedorMain.layoutParams as LinearLayout.LayoutParams
        p1.width = LinearLayout.LayoutParams.MATCH_PARENT
        p1.height = LinearLayout.LayoutParams.WRAP_CONTENT
        p1.bottomMargin = 12
        contenedorMain.layoutParams = p1

        contenedorMain.orientation = LinearLayout.HORIZONTAL
        contenedorMain.setPadding( 3 , 4 , 0 , 4 )
        contenedorMain.setBackgroundColor( ContextCompat.getColor( this , R.color.rojoLigero ) )

        contenedorMain.setOnClickListener {
            val lanzar = Intent(this@MainActivity , PantallaNota::class.java)
            lanzar.putExtra( "esNuevoRegistro" , false )
            lanzar.putExtra( "titulo" , nota.titulo )
            lanzar.putExtra( "contenido" , nota.contenido )
            lanzar.putExtra( "fecha" , nota.fecha )
            lanzar.putExtra( "id" , nota.id )
            startActivity(lanzar)
            finish()

        }

        //=================================================
        // IMAGE VIEW
        //=================================================
        val imagenNota = ImageView( this )
        contenedorMain.addView( imagenNota )

        val p2 = imagenNota.layoutParams as LinearLayout.LayoutParams
        p2.width = LinearLayout.LayoutParams.WRAP_CONTENT
        p2.height = LinearLayout.LayoutParams.MATCH_PARENT
        p2.gravity = Gravity.CENTER_VERTICAL
        imagenNota.layoutParams = p2

        imagenNota.setImageResource( R.drawable.icono_nota )
        imagenNota.setBackgroundColor( ContextCompat.getColor( this , R.color.rojoOscuro ) )
        imagenNota.setColorFilter( ContextCompat.getColor( this , R.color.rojoLigero ) , android.graphics.PorterDuff.Mode.SRC_IN )


        //=================================================
        // LINEAR LAYOUT INFORMACION
        //=================================================
        val contenedorTexto = LinearLayout(this)
        contenedorMain.addView(contenedorTexto)

        val p3 = contenedorTexto.layoutParams as LinearLayout.LayoutParams
        p3.width = 1
        p3.height = LinearLayout.LayoutParams.WRAP_CONTENT
        p3.weight = 2f
        contenedorTexto.layoutParams = p3
        contenedorTexto.orientation = LinearLayout.VERTICAL

        //=================================================
        // TEXT VIEW
        //=================================================
        val txtTitulo = TextView( this )
        contenedorTexto.addView(txtTitulo)

        val p4 = txtTitulo.layoutParams as LinearLayout.LayoutParams
        p4.width = LinearLayout.LayoutParams.WRAP_CONTENT
        p4.height = LinearLayout.LayoutParams.WRAP_CONTENT
        p4.gravity = Gravity.CENTER
        txtTitulo.layoutParams = p4

        txtTitulo.setPadding( 3 , 0 , 3 , 3 )
        txtTitulo.setText( nota.titulo )
        txtTitulo.setTextSize( TypedValue.COMPLEX_UNIT_SP , 21f )
        txtTitulo.setTypeface(Typeface.DEFAULT_BOLD)
        txtTitulo.setTextColor( ContextCompat.getColor( this , R.color.rojoOscuro ) )

        //=================================================
        // TEXT VIEw
        //=================================================
        val txtContenido = TextView( this )
        contenedorTexto.addView( txtContenido )

        val p5 = txtContenido.layoutParams as LinearLayout.LayoutParams
        p5.width = LinearLayout.LayoutParams.WRAP_CONTENT
        p5.height = LinearLayout.LayoutParams.WRAP_CONTENT
        p5.gravity = Gravity.CENTER
        txtContenido.layoutParams = p5

        txtContenido.setPadding( 3 , 0 , 3 , 0 )
        txtContenido.setText( nota.contenido )
        txtContenido.setTextSize( TypedValue.COMPLEX_UNIT_SP , 17f )
        txtContenido.setTypeface(Typeface.DEFAULT_BOLD)
        txtContenido.setTextColor( ContextCompat.getColor( this , R.color.rojoOscuro ) )

        //=================================================
        // TEXT VIEW
        //=================================================
        val txtFecha = TextView( this )
        contenedorTexto.addView( txtFecha )

        val p6 = txtFecha.layoutParams as LinearLayout.LayoutParams
        p6.width = LinearLayout.LayoutParams.WRAP_CONTENT
        p6.height = LinearLayout.LayoutParams.WRAP_CONTENT
        p6.gravity = Gravity.CENTER
        txtFecha.layoutParams = p6

        txtFecha.setPadding( 3 , 0 , 3 , 0 )
        val f = Fecha(Calendar.getInstance())
        f.convertirFechaSQL(nota.fecha)
        txtFecha.setText( f.getFecha())
        txtFecha.setTextSize( TypedValue.COMPLEX_UNIT_SP , 18f )
        txtFecha.setTypeface(Typeface.DEFAULT_BOLD)
        txtFecha.setTextColor( ContextCompat.getColor( this , R.color.rojoOscuro ) )
    }


    fun insertarnube(){
        val registros = ControladorNota(this).filtrarTodo()
       // ToastPersonalizado(this,registros.size.toString()).show()
        registros.forEach{
            var datos = hashMapOf("ID" to it.id, "TITULO" to it.titulo, "CONTENIDO" to it.contenido, "FECHA" to it.fecha)
            val nota = it
            baseDatos.collection("nota").add(datos as Any).addOnSuccessListener {
                //numsubidos+=1
                ToastPersonalizado(this, "La nota"+nota.id+" con el titulo "+nota.titulo +" se ha subido").show()
            }
                .addOnFailureListener {
                    ToastPersonalizado(this, "No se puedo guardar en la nube").show()
                    //subidos = false
                }

        }
    }




}//fin