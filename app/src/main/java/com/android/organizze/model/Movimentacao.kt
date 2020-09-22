package com.android.organizze.model

import android.util.Log
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.Base64Custom
import com.android.organizze.helper.DateCustom

class Movimentacao constructor(
      var data : String = ""
    , var categoria : String = ""
    , var descricao : String = ""
    , var tipo : String = ""
    , var valor : Double
) {

    companion object{
        const val DESPESA = "DESPESA";
        const val RECEITA = "RECEITA";
        const val PATH = "movimnetacoes";
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
                        Log.w(Movimentacao.PATH, "Movimnetação salva com sucesso");

                    } else {
                        Log.w(Movimentacao.PATH, task.exception.toString());
                        throw task.exception!!;
                    }
                }

    }

}