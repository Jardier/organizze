package com.android.organizze.config

import com.google.firebase.auth.FirebaseAuth

/**
 * Padrão Singleton
 */
class FireBaseConfig private constructor(){

    private object AUTENTICACAO {
        val AUTENTICACAO = FirebaseAuth.getInstance();
    }

    companion object {
        val autenticacao: FirebaseAuth by lazy { AUTENTICACAO.AUTENTICACAO}
    }

}