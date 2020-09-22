package com.android.organizze.model

import android.util.Log
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.Base64Custom
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario constructor( var idUsuario: String = ""
                              , var nome: String = ""
                              , var email: String = ""
                              , @get:Exclude var senha : String = ""
                              , var despesaTotal : Double = 0.00
                              , var receitaTotal : Double = 0.00 ) {

    companion object {
        /**
         * Retorna o email do Usuário na Base64
         */
        fun getIdUsuario() : String {
            val autenticacao = FireBaseConfig.autenticacao;
            val idUsuario : String = Base64Custom.codificarBase64(autenticacao.currentUser?.email.toString());

            return  idUsuario;
        }

        const val PATH = "usuarios";
    }

    fun salvar() {
        val firebase = FireBaseConfig.reference;

        firebase.child(Usuario.PATH)
            .child(this.idUsuario)
            .setValue(this)
            .addOnCompleteListener{task ->
                if(task.isSuccessful) {
                    Log.i(Usuario.PATH, "Dados o usuário salvo com sucesso.");
                }else {
                    Log.e(Usuario.PATH, task.exception.toString());
                }
            };

    }

    fun atualizarDepesa(valor : Double) {
        val dataBase = FireBaseConfig.reference;

        dataBase.child(Usuario.PATH)
            .child(Usuario.getIdUsuario())
            .child("despesaTotal").setValue(valor)
            .addOnCompleteListener { task ->

                if(task.isSuccessful) {
                    Log.w(Usuario.PATH, "Total depesa atualizado com sucesso");
                } else {
                    Log.e(Usuario.PATH, "Ocorreu um erro ao autializar o Total de despesa", task.exception);
                    throw task.exception!!;
                }
            }
    }


}