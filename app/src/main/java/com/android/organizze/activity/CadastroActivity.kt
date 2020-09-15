package com.android.organizze.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.organizze.R
import com.android.organizze.config.FireBaseConfig
import com.android.organizze.helper.Base64Custom
import com.android.organizze.model.Usuario
import com.android.organizze.helper.MensageCustom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

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

        editTextNome.requestFocus();

        //Ação botão Cadastrar
        buttonCadastrar.setOnClickListener(View.OnClickListener {

            if(formularioValido()) {
                autenticacao =  FireBaseConfig.autenticacao;

                autenticacao.createUserWithEmailAndPassword(usuario.email, usuario.senha)
                    .addOnCompleteListener(this){task ->
                        if(task.isSuccessful) {
                            val idUsuario = Base64Custom.codificarBase64(usuario.email);
                            usuario.idUsuario = idUsuario;
                            usuario.salvar();
                            finish();

                        } else {
                            var excecao : String = "";
                            try {
                                throw task.exception!!

                            } catch(e : FirebaseAuthWeakPasswordException) {
                                excecao = "Digite uma senha mais forte!";
                            } catch(e : FirebaseAuthInvalidCredentialsException) {
                                excecao = "Por favor, digite um e-mail válido";
                            } catch (e : FirebaseAuthUserCollisionException) {
                                excecao = "Este e-mail já foi cadastrado";
                            } catch (e : Exception) {
                                excecao = "Erro ao cadastrar usário ${e.message}";
                            }

                            Toast.makeText(this, excecao, Toast.LENGTH_LONG).show();

                        }
                    }

            }

        })

    }

    private fun formularioValido() : Boolean {
        val nome  : String = editTextNome.text.toString().trim();
        val email : String = editTextEmail.text.toString().trim();
        val senha : String = editTextSenha.text.toString().trim();

        usuario = Usuario(nome = nome, email = email, senha = senha);

        if(TextUtils.isEmpty(usuario.nome)) {
            MensageCustom.error(this, editTextNome, "O nome é obrigatório");
            return false;
        }else if(TextUtils.isEmpty(usuario.email)) {
            MensageCustom.error(this, editTextEmail, "O email é obrigatório");
            return false;
        }else if(TextUtils.isEmpty(usuario.senha)) {
            MensageCustom.error(this, editTextSenha, "A senha é obrigatória");
            return false;
        }

        return true
    }

}
