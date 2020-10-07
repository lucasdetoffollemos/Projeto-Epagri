package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.R;

import java.util.Arrays;

public class PropriedadeActivity extends AppCompatActivity {
    private Button bt_proximo;
    private EditText et_nomePropriedade;

    private String nomeUsuario;
    private int  CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedade);

        inicializa();
        setListener();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        et_nomePropriedade = findViewById(R.id.et_nomePropriedade);
        bt_proximo = findViewById(R.id.bt_proximo);
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListener(){
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPiquete();
            }
        });
    }

    /**
     * Método chamado ao clicar no botão Próximo Passo.
     */
    public void cadastrarPiquete() {
        String nomePropriedade = et_nomePropriedade.getText().toString();

        if(!nomePropriedade.equals("")){
            Intent i = new Intent(PropriedadeActivity.this, PiqueteActivity.class);
            i.putExtra("nome_propriedade", nomePropriedade);
            startActivityForResult(i, CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY);
        }

        else {
            Toast.makeText(getApplicationContext(), "Insira o nome da propriedade", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método utilizado para enviar de volta para a IndexActivity o nome do usuário caso ele clique no botão voltar.
     */
    public void enviaResposta(){
        Intent intent = new Intent();
        intent.putExtra("nomeUsuario", nomeUsuario);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
                enviaResposta();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Método responsável por lidar com as respostas enviadas da activity Piquete.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                Toast.makeText(getApplicationContext(), "Oi bebe!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

