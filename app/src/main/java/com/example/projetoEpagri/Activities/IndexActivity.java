package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetoEpagri.R;

public class IndexActivity extends AppCompatActivity {
    private TextView tv_bemVindo;
    private Button bt_cadastrarPropriedade;
    private String nomeUsuario;
    private int codigoRequisicao = 1; //Código para identificar a activity no método onActivityResult.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //Pegando a mensagem da activity anterior(MainActivity)
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        tv_bemVindo = findViewById(R.id.tv_bemVindo);
        tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);

        bt_cadastrarPropriedade = findViewById(R.id.bt_index);
        bt_cadastrarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPropiedade();
            }
        });
    }

    /**
     * Pegando o nome do usuário e enviando para a activity propriedade, para depois retorná-lo
     */
    public void cadastrarPropiedade() {
        Intent i = new Intent(IndexActivity.this, PropriedadeActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        startActivityForResult(i, this.codigoRequisicao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.codigoRequisicao) {
            if(resultCode == Activity.RESULT_OK){
                nomeUsuario = data.getStringExtra("resultado");
                tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}