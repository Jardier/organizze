package com.android.organizze.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.organizze.R
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.model.Usuario
import com.android.organizze.helper.MessageCustom
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail : EditText;
    private lateinit var editTextSenha : EditText;
    private lateinit var buttonEntrar : Button;

    private lateinit var usuario: Usuario;
    private lateinit var autenticacao: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextLoginEmail);
        editTextSenha = findViewById(R.id.editTextLoginSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        editTextEmail.requestFocus();


        //Ação botão Entrar
        buttonEntrar.setOnClickListener(View.OnClickListener {

            if(formularioValido()) {
              autenticacao = FireBaseConfig.autenticacao;
                autenticacao.signInWithEmailAndPassword(usuario.email, usuario.senha)
                    .addOnCompleteListener { task: Task<AuthResult> ->

                        if(task.isSuccessful) {
                           exibirTelaPrincipal();
                        } else {
                            var excecao : String = ""
                            try {
                                throw task.exception!!;
                            } catch (e : FirebaseAuthInvalidUserException) {
                                excecao = "Usuário não está cadastrado";
                            } catch (e : FirebaseAuthInvalidCredentialsException) {
                                excecao = "E-mail e/ou senha não correspondem a um usuário cadastrado";
                            } catch (e : Exception) {
                                excecao = "Ocorreu um erro ao validar o usuário. ${e.message}";
                            }

                            Toast.makeText(this, excecao, Toast.LENGTH_LONG).show()
                        }
                    }

            }
        })
    }

    private fun formularioValido() : Boolean {
        val email : String = editTextEmail.text.toString().trim();
        val senha : String = editTextSenha.text.toString().trim();

        if(TextUtils.isEmpty(email)) {
            MessageCustom.error(this, editTextEmail, "O email é obrigatório");
            return false;
        }else if(TextUtils.isEmpty(senha)) {
            MessageCustom.error(this, editTextSenha, "A senha é obrigatória");
            return false;
        }

        usuario = Usuario(email = email, senha = senha);
        return true;
    }

    private fun exibirTelaPrincipal() {
        val intent = Intent(this, PrincipalActivity::class.java);
        startActivity(intent);
        finish();
    }
}
