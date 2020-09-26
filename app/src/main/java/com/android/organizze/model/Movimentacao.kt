package com.android.organizze.model

import android.util.Log
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.DateCustom
import com.google.firebase.database.Exclude
import java.io.Serializable

class Movimentacao constructor (
      var data : String = ""
    , var categoria : String = ""
    , var descricao : String = ""
    , var tipo : String = ""
    , var valor : Double = 0.0
    , @get:Exclude var key : String? = null
) : Serializable {

    companion object{
        const val DESPESA = "DESPESA";
        const val RECEITA = "RECEITA";
        const val PATH = "movimentacoes";
    }

    fun salvar() {
        val firebase = FireBaseConfig.reference;
        val mesAno = DateCustom.anoData(this.data);
      
        firebase.child(Movimentacao.PATH)
                .child(Usuario.getIdUsuario())
                .child(mesAno)
                .push()
                .setValue(this)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.w(Movimentacao.PATH, "Movimentação salva com sucesso");

                    } else {
                        Log.w(Movimentacao.PATH, task.exception.toString());
                        throw task.exception!!;
                    }
                }

    }

}