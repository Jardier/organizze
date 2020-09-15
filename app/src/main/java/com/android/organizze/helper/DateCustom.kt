package com.android.organizze.helper

import java.text.SimpleDateFormat

class DateCustom {

    companion object {

        fun dataAtual() : String {
            val data : Long = System.currentTimeMillis();
            val dateFormat = SimpleDateFormat("dd/MM/yyyy");

            return  dateFormat.format(data);
        }
    }
}