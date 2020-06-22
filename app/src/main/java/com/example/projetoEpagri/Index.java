package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        //Pegando a mensagem da activity anterior(MainActivity)
        Intent intent = getIntent();
        String nomeEnviado = intent.getStringExtra(MainActivity.mensagem_extra);

        TextView bem_vindo = findViewById(R.id.tv_bemVindo);
        bem_vindo.setText("Seja bem vindo " + nomeEnviado);
    }
}
