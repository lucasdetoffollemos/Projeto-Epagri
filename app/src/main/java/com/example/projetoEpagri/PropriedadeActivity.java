package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PropriedadeActivity extends AppCompatActivity {

    public Button bt_piquete;
    public static String mensagem_extra;
    public static String mensagem_extra2;
    public EditText nomeProp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedade);

        nomeProp = findViewById(R.id.pt_nomePropriedade);


        bt_piquete = findViewById(R.id.bt_cadastroProp);
        bt_piquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                levaParaPiquete();
            }
        });



    }

    public void levaParaPiquete() {
        Intent i = new Intent(PropriedadeActivity.this, PiqueteActivity.class);
        String nomePropriedade = nomeProp.getText().toString();
        i.putExtra(mensagem_extra2, nomePropriedade);
        startActivity(i);
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

