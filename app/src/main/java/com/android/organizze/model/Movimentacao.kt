package com.android.organizze.model

import android.util.Log
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.Base64Custom
import com.android.organizze.helper.DateCustom
import java.math.BigDecimal

class Movimentacao constructor(
      var data : String = ""
    , var categoria : String = ""
    , var descricao : String = ""
    , var tipo : String = ""
    , var valor : Double
) {

    fun salvar() {
        val firebase = FireBaseConfig.reference;
        val idUsuario = getIdUsuario();
        val mesAno = DateCustom.anoData(this.data);

        firebase.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.i("MOVIMENTACAO", "Movimnetação salva com sucesso");
                    } else {
                        Log.i("MOVIMENTACAO", task.exception.toString());
                    }
                }

    }

    /**
     * Retorna o email do Usuário na Base64
     */
    private fun getIdUsuario() : String {
        val autenticacao = FireBaseConfig.autenticacao;
        val idUsuario : String = Base64Custom.codificarBase64(autenticacao.currentUser?.email.toString());

        return  idUsuario;
    }
}