package com.android.organizze.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.organizze.R
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.Base64Custom
import com.android.organizze.helper.DateCustom
import com.android.organizze.helper.MessageCustom
import com.android.organizze.model.Movimentacao
import com.android.organizze.model.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class DespesaActivity : AppCompatActivity() {

    lateinit var editTextValor : EditText;
    lateinit var inputEditTextData: TextInputEditText;
    lateinit var inputEditTextCategoria: TextInputEditText;
    lateinit var inputEditTextDescricao: TextInputEditText;
    lateinit var fabSalva : FloatingActionButton;

    lateinit var movimentacao: Movimentacao;
    lateinit var usuario: Usuario;

    var despesaTotal : Double = 0.00;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        editTextValor =  findViewById(R.id.editTextValor);
        inputEditTextData = findViewById(R.id.inputEditTextData);
        inputEditTextCategoria = findViewById(R.id.inputEditTextCategoria);
        inputEditTextDescricao = findViewById(R.id.inputEditTextDescricao);
        fabSalva = findViewById(R.id.fabSalvar);

        editTextValor.requestFocus();

        //Recuperando despesa
        despesaTotal =  recuperarDespesaTotal();

        fabSalva.setOnClickListener { v: View? -> 
           
          if(formularioValido()) {

             try {
                 movimentacao.salvar();
                 despesaTotal = despesaTotal + movimentacao.valor;

                 usuario.atualizarDepesa(despesaTotal);

             } catch (e : Exception){
                 Toast.makeText(this, "Ocorreu um erro ao salvar a movimentação: ${e.message}", Toast.LENGTH_LONG).show();
             }

          }
        }

    }

    override fun onStart() {
        super.onStart()
        inputEditTextData.setText(DateCustom.dataAtual());
    }

    private fun formularioValido() : Boolean {
        val valor : String   =  editTextValor.text.toString().trim();
        val data : String = inputEditTextData.text.toString().trim();
        val categoria : String = inputEditTextCategoria.text.toString().trim();
        val descricao : String = inputEditTextDescricao.text.toString().trim();

        if(TextUtils.isEmpty(valor) || valor.toDouble().equals(0.0)) {
            MessageCustom.error(this, editTextValor, "Valor obrigatório e maior que zero");
            return false;
        }else if(TextUtils.isEmpty(data)) {
            MessageCustom.error(this, inputEditTextData, "A data é obrigatória");
            return false;
        }else if(TextUtils.isEmpty(categoria)) {
            MessageCustom.error(this, inputEditTextCategoria, "A categoria é obrigatória");
            return false;
        }else if(TextUtils.isEmpty(descricao)) {
            MessageCustom.error(this, inputEditTextDescricao, "A descrição é obrigatória");
        }

        movimentacao = Movimentacao(data, categoria, descricao, Movimentacao.DESPESA, valor.toDouble());

        return true;
    }

    private fun recuperarDespesaTotal() : Double {
        var total : Double = 0.00;

        FireBaseConfig.reference.child(Usuario.PATH)
            .child(Usuario.getIdUsuario())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    usuario = dataSnapshot.getValue<Usuario>(Usuario::class.java)!!;
                    total = usuario?.despesaTotal ?: 0.00;
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("ERROR", databaseError.message);
                }
            })


        return total;
    }
}
