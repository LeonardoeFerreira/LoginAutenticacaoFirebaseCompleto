package com.playxcodes.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.playxcodes.organizze.R;

import com.playxcodes.organizze.config.ConfiguracaoFirebase;
import com.playxcodes.organizze.helper.Base64Custom;
import com.playxcodes.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;

    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmailCadastrar);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //validar se os campos foram preenchidos

                if(!textoNome.isEmpty()){
                        if(!textoEmail.isEmpty()){
                                if(!textoSenha.isEmpty()){

                                    usuario = new Usuario();
                                    usuario.setNome(textoNome);
                                    usuario.setEmail(textoEmail);
                                    usuario.setSenha(textoSenha);
                                    cadastrarUsuario();

                                }else{
                                    Toast.makeText(CadastroActivity.this,
                                            "Preencha a senha", Toast.LENGTH_SHORT).show();
                                }
                        }else{
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha o email", Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cadastrarUsuario(){
        //recuperando a instancia do firebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //verificar se o cadastro deu certo
                if(task.isSuccessful()){
                    //Toast.makeText(CadastroActivity.this,"Sucesso ao cadastrar usuario!", Toast.LENGTH_SHORT).show();

                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                     finish();
                }else{

                    //tratamento de exce????o senha, email
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um e-mail valido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao=" Esta conta ja foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuario" + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(CadastroActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}