package com.android.organizze.helper

import java.text.SimpleDateFormat
import java.util.*

class DateCustom {

    companion object {

        fun dataAtual() : String {
            val data : Long = System.currentTimeMillis();
            val dateFormat = SimpleDateFormat("dd/MM/yyyy");

            return  dateFormat.format(data);
        }

        fun anoData(data : String) : String {
            val dataAux = data.split("/");
            val mes = dataAux.get(1);
            val ano = dataAux.get(2);
            return mes.plus(ano);
        }

        fun dataFormatadada(data : Date, pattern : String) : String {
            val dateFormat = SimpleDateFormat(pattern)
            return dateFormat.format(data);
        }
    }
}