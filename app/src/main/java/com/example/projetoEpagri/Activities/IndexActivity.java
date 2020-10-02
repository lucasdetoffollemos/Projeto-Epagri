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

        inicializa();
        setListeners();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        //Recebe a mensagem (nome do usuário) da activity anterior (LoginActivity).
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        tv_bemVindo = findViewById(R.id.tv_bemVindo);
        tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);

        bt_cadastrarPropriedade = findViewById(R.id.bt_index);
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListeners(){
        bt_cadastrarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPropiedade();
            }
        });
    }

    /**
     * Método responsável por iniciar a PropriedadeActivity.
     * O nome precisa ser enviado junto, caso contrário, quando o usuário retorna, ele é perdido.
     */
    public void cadastrarPropiedade() {
        Intent i = new Intent(IndexActivity.this, PropriedadeActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        startActivityForResult(i, this.codigoRequisicao);
    }

    /**
     * Método responsável por lidar com as respostas enviadas da activity Propriedade.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.codigoRequisicao) {
            if(resultCode == Activity.RESULT_OK){
                nomeUsuario = data.getStringExtra("nomeUsuario");
                tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}