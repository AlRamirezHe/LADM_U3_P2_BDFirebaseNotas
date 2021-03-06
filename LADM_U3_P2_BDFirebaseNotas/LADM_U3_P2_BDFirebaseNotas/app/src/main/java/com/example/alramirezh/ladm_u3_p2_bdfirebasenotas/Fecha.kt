package com.example.alramirezh.ladm_u3_p2_bdfirebasenotas

import java.util.*

class Fecha(fecha: Calendar) {

    val fecha = fecha

    fun getFecha() : String {
        return getDia() + " / " + getNombreMes() + " / " + getYear() + "   " + getHora() + ":" + getMinutos() + ":" + getSegundos()
    }

    fun convertirFechaSQL(fSQL: String)  {
        val separador = fSQL.split(" ")
        val pFecha = separador[0].split("-")
        val pHora = separador[1].split(":")
        fecha.set(pFecha[0].toInt(),pFecha[1].toInt()-1,pFecha[2].toInt(),pHora[0].toInt(),pHora[1].toInt(),pHora[2].toInt())

    }

    fun getFechaSQL() : String {
        return getYear() + "-" + getMes() + "-" + getDia() + " " + getHora() + ":" + getMinutos() + ":" + getSegundos()
    }





    fun getDia(): String {
        var dia = fecha.get( Calendar.DAY_OF_MONTH ).toString()
        if( dia.toInt() < 10){ dia = "0$dia" }
        return dia
    }

    fun getMes(): String {
        var mes = (fecha.get( Calendar.MONTH ) + 1).toString()
        if( mes.toInt() < 10){ mes = "0$mes" }
        return mes
    }

    fun getHora(): String {
        var hora = (fecha.get( Calendar.HOUR_OF_DAY )).toString()
        if( hora.toInt() < 10){ hora = "0$hora" }
        return hora
    }

    fun getMinutos(): String {
        var min = (fecha.get( Calendar.MINUTE )).toString()
        if( min.toInt() < 10){ min = "0$min" }
        return min
    }

    fun getSegundos(): String {
        var seg = (fecha.get( Calendar.SECOND )).toString()
        if( seg.toInt() < 10){ seg = "0$seg" }
        return seg
    }

    fun getNombreMes(): String {
        var mes = ""
        when( fecha.get( Calendar.MONTH ) + 1 )
        {
            1 -> { mes = "Enero"}
            2 -> { mes = "Febrero"}
            3 -> { mes = "Marzo"}
            4 -> { mes = "Abril"}
            5 -> { mes = "Mayo"}
            6 -> { mes = "Junio"}
            7 -> { mes = "Julio"}
            8 -> { mes = "Agosto"}
            9 -> { mes = "Septiembre"}
            10 -> { mes = "Octubre" }
            11 -> { mes = "Noviembre"}
            12 -> {mes = "Diciembre"}
        }
        return mes
    }


    fun getNombreMes( mesSQL: String): String {
        var mes = ""
        when( mesSQL )
        {
            "01" -> { mes = "Enero"}
            "02" -> { mes = "Febrero"}
            "03" -> { mes = "Marzo"}
            "04" -> { mes = "Abril"}
            "05" -> { mes = "Mayo"}
            "06" -> { mes = "Junio"}
            "07" -> { mes = "Julio"}
            "08" -> { mes = "Agosto"}
            "09" -> { mes = "Septiembre"}
            "10" -> { mes = "Octubre" }
            "11" -> { mes = "Noviembre"}
            "12" -> {mes = "Diciembre"}
        }
        return mes
    }

    fun getYear(): String {
        return fecha.get( Calendar.YEAR ).toString()
    }
}