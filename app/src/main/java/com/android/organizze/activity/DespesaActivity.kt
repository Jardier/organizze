package com.android.organizze.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.android.organizze.R
import com.android.organizze.helper.DateCustom
import com.android.organizze.model.Movimentacao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal

class DespesaActivity : AppCompatActivity() {

    lateinit var editTextValor : EditText;
    lateinit var inputEditTextData: TextInputEditText;
    lateinit var inputEditTextCategoria: TextInputEditText;
    lateinit var inputEditTextDescricao: TextInputEditText;
    lateinit var fabSalva : FloatingActionButton;
    
    companion object{
        const val DESPESA = "DESPESA";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        editTextValor =  findViewById(R.id.editTextValor);
        inputEditTextData = findViewById(R.id.inputEditTextData);
        inputEditTextCategoria = findViewById(R.id.inputEditTextCategoria);
        inputEditTextDescricao = findViewById(R.id.inputEditTextDescricao);
        fabSalva = findViewById(R.id.fabSalvar);


        fabSalva.setOnClickListener { v: View? -> 
           
            //Objeto Movimnetação
            val doubleValor = editTextValor.text.toString().toDouble();
            val movimentacao = Movimentacao(
                  inputEditTextData.text.toString()
                , inputEditTextCategoria.text.toString()
                , inputEditTextDescricao.text.toString()
                , DespesaActivity.DESPESA
                , doubleValor
            );
            
            movimentacao.salvar();
            
            
        }

    }

    override fun onStart() {
        super.onStart()
        inputEditTextData.setText(DateCustom.dataAtual());
    }
}
