package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoEpagri.R;

public class PropriedadeActivity extends AppCompatActivity {
    private Button bt_proximo;
    private EditText et_nomePropriedade;
    private EditText et_qtdePiquetes;
    private String nomeUsuario;
    private int codigoRequisicao = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedade);

        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        et_nomePropriedade = findViewById(R.id.et_nomePropriedade);
        et_qtdePiquetes = findViewById(R.id.et_qtdePiquetes);

        bt_proximo = findViewById(R.id.bt_proximo);
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPiquete();
            }
        });
    }

    public void cadastrarPiquete() {
        Intent i = new Intent(PropriedadeActivity.this, PiqueteActivity.class);
        String nomePropriedade = et_nomePropriedade.getText().toString();
        i.putExtra("nome_propriedade", nomePropriedade);


        //startActivity(i);

            String qtdePiquetes = et_qtdePiquetes.getText().toString();

            i.putExtra("qtde_piquetes", qtdePiquetes);
            startActivityForResult(i, this.codigoRequisicao);


    }

    public void enviaResposta(){
        Intent intent = new Intent();
        intent.putExtra("resultado", nomeUsuario);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Método chamado ao clicar no botão voltar do celular
     */
//    @Override
//    public void onBackPressed (){
//        enviaResposta();
//        super.onBackPressed();
//        Toast.makeText(this, "executei ", Toast.LENGTH_SHORT).show();
//
//    }

    /**
     * Método que volta para activity anterior(Index), pela seta de voltar em cima da tela.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                enviaResposta();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}

