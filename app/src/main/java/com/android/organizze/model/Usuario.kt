package com.android.organizze.model

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.organizze.config.FireBaseConfig
import com.google.firebase.database.Exclude

data class Usuario constructor( @get:Exclude var idUsuario: String = ""
                                  , var nome: String = ""
                                  , var email: String = ""
                                  , @get:Exclude var senha : String = "") {


    fun salvar() {
        val firebase = FireBaseConfig.reference;

        firebase.child("usuarios")
            .child(this.idUsuario)
            .setValue(this)
            .addOnCompleteListener{task ->
                if(task.isSuccessful) {
                    Log.i("INFORMAÇÃO", "Sucesso");
                }else {
                    Log.e("ERROR", task.exception.toString());
                }
            };

    }

}