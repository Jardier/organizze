package com.android.organizze.config

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Padrão Singleton
 */
class FireBaseConfig private constructor(){

    private object FIREBASE {
        val AUTENTICACAO = FirebaseAuth.getInstance();
        val REFERENCE = FirebaseDatabase.getInstance().reference
    }


    companion object {
        val autenticacao: FirebaseAuth by lazy { FIREBASE.AUTENTICACAO};
        val reference: DatabaseReference by lazy { FIREBASE.REFERENCE}
    }

}