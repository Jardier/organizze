package com.android.organizze.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.android.organizze.R
import com.android.organizze.helper.DateCustom
import com.google.android.material.textfield.TextInputEditText

class DespesaActivity : AppCompatActivity() {

    lateinit var editTextValor : EditText;
    lateinit var inputEditTextData: TextInputEditText;
    lateinit var inputEditTextCategoria: TextInputEditText;
    lateinit var inputEditTextDescricao: TextInputEditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        editTextValor =  findViewById(R.id.editTextValor);
        inputEditTextData = findViewById(R.id.inputEditTextData);
        inputEditTextCategoria = findViewById(R.id.inputEditTextCategoria);
        inputEditTextDescricao = findViewById(R.id.inputEditTextDescricao);

    }

    override fun onStart() {
        super.onStart()
        inputEditTextData.setText(DateCustom.dataAtual());
    }
}
