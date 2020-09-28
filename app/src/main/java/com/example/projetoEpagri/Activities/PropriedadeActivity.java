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
    private EditText et_qtdePiquetes;
    private String nomeUsuario;
    private int  CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriedade);

        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        et_nomePropriedade = findViewById(R.id.et_nomePropriedade);
//        et_qtdePiquetes = findViewById(R.id.et_qtdePiquetes);

        bt_proximo = findViewById(R.id.bt_proximo);
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarPiquete();
            }
        });
    }

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

//            String qtdePiquetes = et_qtdePiquetes.getText().toString();
//
//            i.putExtra("qtde_piquetes", qtdePiquetes);
//            startActivityForResult(i, this.codigoRequisicao);


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


    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                Piquete p = data.getParcelableExtra("Piquete");
                //int [] arrayRetorno = data.getIntArrayExtra("animais");

                Log.i(" PIQUETE ", " TIPO: "+ p.getTipo() + " COND " + p.getCondicao() + " AREA " + p.getArea()+ " PRODUCAO "+ p.getProdEstimada() + " ARRAY MESES " + Arrays.toString(p.getMeses()) + " TOTAL "+ p.getTotal()+ " SOMA AREAS " + p.getTotalColunaHa() + " TOTAIS MESES "+ Arrays.toString(p.getTotaisMeses()) + " TOTAIS ESTACÃO " + Arrays.toString(p.getTotaisEstacao()));

                Animais a = data.getParcelableExtra("Animal");
                //int [] arrayRetorno = data.getIntArrayExtra("animais");

                Log.i("Animal", "Oi " + a.getCategoria() +" "+ a.getConsumo()+" "+ a.getNumAnimais() +" "+ a.getEntradaMes() +" "+ a.getPesoInicial() +" "+ a.getPesoFinal() +" "+ a.getPesoGanhoVer() +" "+ a.getPesoGanhoOut() +" "+ a.getPesoGanhoInv() +" "+ a.getPesoGanhoPrim() +" "+ Arrays.toString(a.getMeses())+" "+ a.getTotalNumAnimais()+" "+ Arrays.toString(a.getUaHaPorMes()));

            }
        }
    }
}

