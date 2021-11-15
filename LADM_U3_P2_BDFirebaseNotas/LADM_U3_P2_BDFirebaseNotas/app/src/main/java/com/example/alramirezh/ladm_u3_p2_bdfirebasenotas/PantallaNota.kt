package com.example.alramirezh.ladm_u3_p2_bdfirebasenotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_pantalla_nota.*
import java.util.*




class PantallaNota : AppCompatActivity() {


    var nota = Nota( "" , "" , "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_nota)

        //=============================================
        //  ESTABLECER DATOS INICIALES
        //=============================================

        // EVALUAR SI ES NUEVO REGISTRO O MOSTRAR DATOS DE UN VEHICULO
        if( intent.extras!!.getBoolean("esNuevoRegistro") ) {
            val p1 = botonGuardar.layoutParams as LinearLayout.LayoutParams
            p1.width = 0
            p1.height = 0
            botonGuardar.layoutParams = p1

            val p3 = ContenedorFecha.layoutParams as LinearLayout.LayoutParams
            p3.width = 0
            p3.height = 0
            ContenedorFecha.layoutParams = p3


            val p2 = botonEliminar.layoutParams as LinearLayout.LayoutParams
            p2.width = 0
            p2.height = 0
            botonEliminar.layoutParams = p2


            txtTitulo.setText( "Nueva Nota" )
        }
        else {
            try {
                val p1 = botonAgregar.layoutParams as LinearLayout.LayoutParams
                p1.width = 0
                p1.height = 0
                botonAgregar.layoutParams = p1

                txtTitulo.setText( "Nota Seleccionada" )
                nota.titulo=intent.extras!!.getString("titulo")!!
                nota.contenido=intent.extras!!.getString("contenido")!!
                nota.fecha=intent.extras!!.getString("fecha")!!
                nota.id=intent.extras!!.getInt("id")!!

                campoTitulo.setText(nota.titulo)
                campoContenido.setText( nota.contenido )

                val f = Fecha(Calendar.getInstance())
                f.convertirFechaSQL(nota.fecha)
                txtFecha.setText(f.getFecha())

            } catch( er: Exception){ ToastPersonalizado(this , er.message!!).show() }
        }


        //=============================================
        //  EVENTOS
        //=============================================
        botonRegresar.setOnClickListener {
            val lanzar = Intent(this@PantallaNota , MainActivity::class.java)
            startActivity(lanzar)
            finish()
        }





        // BOTON AGREGAR
        botonAgregar.setOnClickListener {
            if( validarCampos() ){
                val insertado = ControladorNota( this ).insertar(
                    Nota(
                        campoTitulo.text.toString() ,
                        campoContenido.text.toString(),
                        Fecha(Calendar.getInstance()).getFechaSQL()
                    )
                )
                if(insertado)
                {
                    ToastPersonalizado(this,"Nota Agregada").show()
                    campoTitulo.setText( "" )
                    campoContenido.setText( "" )

                }
                else{ ToastPersonalizado(this,"Algo salio mal. Intentelo de Nuevo").show() }
            }
        }


        // BOTON ELIMINAR
        botonEliminar.setOnClickListener {


            val eliminado = ControladorNota(this).eliminar(nota.id)
            if (eliminado) {
                ToastPersonalizado(this,"Nota Eliminada").show()
                val lanzar = Intent(this@PantallaNota, MainActivity::class.java)
                startActivity(lanzar)
                finish()
            } else {
                ToastPersonalizado(this,"Algo salio mal. Intentelo de Nuevo").show()
            }

        }


        // BOTON GUARDAR CAMBIOS
        botonGuardar.setOnClickListener {
            if( validarCampos() ){
                nota.titulo = campoTitulo.text.toString()
                nota.contenido = campoContenido.text.toString()
                nota.fecha=Fecha(Calendar.getInstance()).getFechaSQL()

                ControladorNota( this ).guardarCambios( nota )
                ToastPersonalizado(this,"Nota Actualizada").show()
            }
        }


        //========================================
        // BOTONES DE AYUDA
        //========================================
        ayuda_campoTitulo.setOnClickListener {
            ToastPersonalizado( this , "Titulo de la nota" ).show()
        }
        ayuda_campoContenido.setOnClickListener {
            ToastPersonalizado( this , "Contenido de la nota" ).show()
        }


    }







    fun validarCampos(): Boolean {
        val txtTitulo = campoTitulo.text.toString()
        val txtContenido = campoContenido.text.toString()


        if(txtTitulo.isEmpty()){
            ToastPersonalizado(this, "Introduzca el titulo de la nota").show()
            return false
        }
        if(txtContenido.isEmpty()){
            ToastPersonalizado(this, "Introduzca el contenido de la nota").show()
            return false
        }

        return true
    }

}