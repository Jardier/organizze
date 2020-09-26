package com.android.organizze.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.organizze.R
import com.android.organizze.adapter.AdapterMovimentacao
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.DateCustom
import com.android.organizze.model.Movimentacao
import com.android.organizze.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener

import kotlinx.android.synthetic.main.activity_principal.*
import java.text.DecimalFormat

class PrincipalActivity : AppCompatActivity() {

    lateinit var calendarView: MaterialCalendarView;
    lateinit var textViewSaudacao: TextView;
    lateinit var textViewSaldo: TextView;
    lateinit var recyclerViewMovimentacoes: RecyclerView;

    lateinit var dataBase: DatabaseReference;
    lateinit var usuarioEventListener: ValueEventListener;
    lateinit var movimentoEventListener: ValueEventListener;

    lateinit var movimentacoes: ArrayList<Movimentacao>;
    lateinit var dataSelecionada: String;

    var usuarioConsulta : Usuario = Usuario();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Organizze";

       /* fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        textViewSaudacao = findViewById(R.id.textViewSaudacao);
        textViewSaldo = findViewById(R.id.textViewSaldo);
        calendarView = findViewById(R.id.calendarView);
        recyclerViewMovimentacoes = findViewById(R.id.recyclerMovimentacoes);
        movimentacoes = ArrayList();

        configurarCalendarView();
        swipe();

        //criar um adapter
        val adapterMovimento = AdapterMovimentacao(movimentacoes);

        //configurar o recycleview
        recyclerViewMovimentacoes.layoutManager = LinearLayoutManager(this);
        recyclerViewMovimentacoes.setHasFixedSize(true);
        recyclerViewMovimentacoes.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewMovimentacoes.adapter = adapterMovimento;
    }

    override fun onStart() {
        super.onStart();
        recuperarResumo();
        recuperarMovimento();
    }

    override fun onStop() {
        super.onStop()

        Log.i("Evento", "O Evento foi removido.");
        dataBase.removeEventListener(usuarioEventListener);
    }

    //Inserindo o menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId ){
            R.id.menuSair -> {
                FireBaseConfig.autenticacao.signOut();
                startActivity(Intent(this, MainActivity::class.java));
                finish();
            }
        }

        return super.onOptionsItemSelected(item)
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

        //Recupera da data atual do CalendarView
        val calendarDay = calendarView.currentDate;

        dataSelecionada = DateCustom.dataFormatadada(calendarDay.date, "MMyyyy");

        //Recupera a data quando alterada.
        calendarView.setOnMonthChangedListener(OnMonthChangedListener { widget, date ->

            dataSelecionada = DateCustom.dataFormatadada(date.calendar.time, "MMyyyy");
            recuperarMovimento();
        })
    }

    private fun recuperarResumo() {
        var saldo : Double = 0.00;
        val decimalFormat = DecimalFormat("0.00");

        dataBase = FireBaseConfig.reference
                .child(Usuario.PATH)
                .child(Usuario.getIdUsuario());


        Log.i("Evento", "O Evento foi adicionado.");
        usuarioEventListener = dataBase.addValueEventListener(object : ValueEventListener{

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    usuarioConsulta = dataSnapshot.getValue<Usuario>(Usuario::class.java)!!;

                    saldo = usuarioConsulta.receitaTotal - usuarioConsulta.despesaTotal;

                    textViewSaudacao.setText("Olá, ${usuarioConsulta.nome}");
                    textViewSaldo.setText(decimalFormat.format(saldo));

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

    }

    private fun recuperarMovimento() {
        dataBase = FireBaseConfig.reference
            .child(Movimentacao.PATH)
            .child(Usuario.getIdUsuario())
            .child(dataSelecionada);


        movimentoEventListener = dataBase.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                movimentacoes.clear();//Garantir q a Lista esteja vazia

                dataSnapshot.children.forEach { dataSnapshot ->
                    var movimentacao: Movimentacao = Movimentacao();
                    movimentacao = dataSnapshot.getValue<Movimentacao>(Movimentacao::class.java)!!;

                    movimentacoes.add(movimentacao);

                }
                //
                recyclerViewMovimentacoes.adapter!!.notifyDataSetChanged();

            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    private fun swipe() {
        val itemTouchHelperCallback =
            object :
                Callback(){

                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val dragsFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                    val swipeFlags = ItemTouchHelper.START or  ItemTouchHelper.END;

                    return makeMovementFlags(dragsFlags, swipeFlags);
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false;
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Log.i("swipe", "Item foi arrastado");
                }
             };
             ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewMovimentacoes);
        }

}
