package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PropriedadeActivity extends AppCompatActivity {


    public static String mensagem_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedade);

        //Pegando o nome da activity anterior(Index)
        Intent intent = getIntent();
        String nomeEnviado = intent.getStringExtra(PropriedadeActivity.mensagem_extra);
        TextView bem_vindo = findViewById(R.id.textView2);
        bem_vindo.setText(nomeEnviado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    /**
     * Método que volta para activity anterior(Index), pela seta de voltar em cima da tela.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}

