package com.android.organizze.util

import android.content.Context
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import com.android.organizze.R

class Utils {


    companion object {
        fun exibeMensagemError(context : Context,  campo : EditText, mensagem : String?) {
            val icon = AppCompatResources.getDrawable(context, R.drawable.ic_error_red_24dp);
            icon?.setBounds(0,0, icon.intrinsicWidth, icon.intrinsicHeight);

            campo.setError(mensagem, icon);
            campo.requestFocus();
        }
    }

}