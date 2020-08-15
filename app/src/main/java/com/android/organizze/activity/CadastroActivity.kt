package com.android.organizze.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.android.organizze.R
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class CadastroActivity : AppCompatActivity() {

    private lateinit var editTextNome: EditText;
    private lateinit var editTextEmail: EditText;
    private lateinit var editTextSenha: EditText;
    private lateinit var buttonCadastrar: Button;

    private lateinit var autenticacao : FirebaseAuth;
    private lateinit var usuario : Usuario;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);


        //Ação botão Cadastrar
        buttonCadastrar.setOnClickListener(View.OnClickListener {

            if(isUsuarioValido()) {
                autenticacao =  FireBaseConfig.autenticacao;

                autenticacao.createUserWithEmailAndPassword(usuario.email, usuario.senha)
                    .addOnCompleteListener(this){task ->
                        if(task.isComplete) {
                            Toast.makeText(this, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(this, "Ocorreu um erro ao salvar o usuário", Toast.LENGTH_LONG).show();
                        }
                    }

            }

        })

    }

    private fun isUsuarioValido() : Boolean {
        val nome  : String = editTextNome.text.toString().trim();
        val email : String = editTextEmail.text.toString().trim();
        val senha : String = editTextSenha.text.toString().trim();

        usuario = Usuario(nome, email, senha);
        if(TextUtils.isEmpty(usuario.nome)) {
            exibeMensagemError(editTextNome, "O nome é obrigatório")
            return false;
        }else if(TextUtils.isEmpty(usuario.email)) {
            exibeMensagemError(editTextEmail, "O email é obrigatório")
            return false;
        }else if(TextUtils.isEmpty(usuario.senha)) {
            exibeMensagemError(editTextSenha, "A senha é obrigatória");
            return false;
        }

        return true
    }

    //Exibe mensagem de Erro
    private fun exibeMensagemError(campo : EditText, mensagem : String?) {
        val icon = AppCompatResources.getDrawable(this, R.drawable.ic_error_red_24dp);
        icon?.setBounds(0,0, icon.intrinsicWidth, icon.intrinsicHeight);

        campo.setError(mensagem, icon);
        campo.requestFocus();
    }
}
