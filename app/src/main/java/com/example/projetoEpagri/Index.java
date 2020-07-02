package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Index extends AppCompatActivity {

    public Button bt_propriedade;
    public static String mensagem_extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        //Pegando a mensagem da activity anterior(MainActivity)
        Intent intent = getIntent();
        String nomeEnviado = intent.getStringExtra(MainActivity.mensagem_extra);


        TextView bem_vindo = findViewById(R.id.tv_bemVindo);
        bem_vindo.setText("Seja bem vindo " + nomeEnviado);




        bt_propriedade = findViewById(R.id.bt_index);
        bt_propriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                levaParaPropiedade();
            }
        });
    }

    /**
     * Pegando o nome do usuário e enviando para a activity propriedade, para depois retorná-lo
     */
    public void levaParaPropiedade() {
        Intent i = new Intent(Index.this, PropriedadeActivity.class);
        startActivity(i);
    }
}