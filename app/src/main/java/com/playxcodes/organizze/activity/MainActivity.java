package com.playxcodes.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.playxcodes.organizze.R;
import com.playxcodes.organizze.activity.CadastroActivity;
import com.playxcodes.organizze.activity.LoginActivity;
import com.playxcodes.organizze.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificaUsuarioLogado();
    }

    //criando dois metodos para conta
    public void btEntrar(View view){
        startActivity( new Intent(this, LoginActivity.class));

    }


    public void btCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));

    }

    public void verificaUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();

        if(autenticacao.getCurrentUser() != null){
           abrirTelaPrincipal();
        }
    }

    //abrindo a tela principal
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));

    }
}