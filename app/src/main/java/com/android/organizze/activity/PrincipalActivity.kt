package com.android.organizze.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.organizze.R

import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

      /*  fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    public fun adicionarDespesa(view: View) {
        Toast.makeText(this,"Adicionar Despesa", Toast.LENGTH_LONG).show();
    }

    public fun adicionarReceita(view: View) {
        Toast.makeText(this,"Adicionar Receita", Toast.LENGTH_LONG).show();
    }
}
