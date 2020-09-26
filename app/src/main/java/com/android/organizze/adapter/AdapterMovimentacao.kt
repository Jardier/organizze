package com.android.organizze.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.organizze.R
import com.android.organizze.model.Movimentacao
import kotlinx.android.synthetic.main.adapter_movimentacao.view.*
import java.text.DecimalFormat

class AdapterMovimentacao(val movimentacoes : List<Movimentacao>) : RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder>() {

    lateinit var context: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context;

        val view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.adapter_movimentacao, parent, false);

        return MyViewHolder(view);
    }

    override fun getItemCount(): Int {
        return movimentacoes.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val decimalFormat = DecimalFormat("#,###.00");

        val movimentacao = movimentacoes[position];
        holder.titulo.text = movimentacao.descricao;
        holder.categoria.text = movimentacao.categoria;
        holder.valor.text = decimalFormat.format(movimentacao.valor);
        holder.valor.setTextColor(ContextCompat.getColor(context,R.color.colorAccentReceita))

        //Configurando a cor de exibição do valor
        if(movimentacao.tipo.equals(Movimentacao.DESPESA)) {
            holder.valor.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            holder.valor.text = ("-${movimentacao.valor.toString()}")
        }

    }


    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.textAdapterTitulo);
        val valor : TextView = itemView.findViewById(R.id.textAdapterValor);
        val categoria: TextView =  itemView.findViewById(R.id.textAdapterCategoria);
    }

}

