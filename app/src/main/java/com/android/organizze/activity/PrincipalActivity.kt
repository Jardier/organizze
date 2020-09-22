package com.android.organizze.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.organizze.R
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener

import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {

    lateinit var calendarView: MaterialCalendarView;
    lateinit var textViewSaudacao: TextView;
    lateinit var textViewSaldo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

      /*  fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        textViewSaudacao = findViewById(R.id.textViewSaudacao);
        textViewSaldo = findViewById(R.id.textViewSaldo);
        calendarView = findViewById(R.id.calendarView);
        configurarCalendarView();
    }

    public fun adicionarDespesa(view: View) {
        startActivity(Intent(this, DespesaActivity::class.java));
    }

    public fun adicionarReceita(view: View) {
        startActivity(Intent(this, ReceitaActivity::class.java));
    }

    private fun configurarCalendarView() {
        val mes = arrayOf<CharSequence>("Janeiro","Feveriro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro");
        calendarView.setTitleMonths(mes);
        
        calendarView.setOnMonthChangedListener(OnMonthChangedListener { widget, date ->
            Log.w("DATA", date.toString());
        })
    }
}
