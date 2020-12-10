package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projetoEpagri.Fragments.FragmentEditarDados;
import com.example.projetoEpagri.R;

public class EditarDadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dados);

        inicializa();
    }

    public void inicializa(){
        Intent intent = getIntent();
        String dados = intent.getStringExtra("dados");

        Fragment fragment;
        FragmentTransaction transaction;

        if(dados != null){
            if(dados.equals("dados_norte")){
                fragment = FragmentEditarDados.newInstance(dados);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ll_fragment_edit, fragment);
                transaction.commit();
            }
            else if(dados.equals("dados_sul")){
                fragment = FragmentEditarDados.newInstance(dados);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ll_fragment_edit, fragment);
                transaction.commit();
            }
        }
    }

    public void clicarVoltarTabs(View view){
        finish();
    }
}