package com.android.organizze.model

import android.util.Log
import com.android.organizze.config.FireBaseConfig
import com.google.firebase.database.Exclude

data class Usuario constructor( @get:Exclude var idUsuario: String = ""
                                  , var nome: String = ""
                                  , var email: String = ""
                                  , @get:Exclude var senha : String = ""
                                  , var despesaTotal : Double = 0.00
                                  , var receitaTotal : Double = 0.00 ) {


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