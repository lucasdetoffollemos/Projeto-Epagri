package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class PropriedadeActivity extends AppCompatActivity {
    private String nomePropriedade, regiao;
    private EditText et_nomePropriedade;
    private Button bt_proximo;
    private Spinner sp_regiao;

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
        //Toast.makeText(PropriedadeActivity.this, nomeUsuario, Toast.LENGTH_SHORT).show();

        et_nomePropriedade = findViewById(R.id.et_nomePropriedade);
        sp_regiao = findViewById(R.id.sp_regiao);
        bt_proximo = findViewById(R.id.bt_levaPiquete);

        //Método para inicializar o Spinner com as regiões.
        escolherRegiao();
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListener(){
        sp_regiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){ //cfa
                    regiao = "cfa";
                }
                else if(position == 1){ //cfb
                    regiao = "cfb";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomePropriedade = et_nomePropriedade.getText().toString();

                if(!nomeUsuario.isEmpty()){
                    vaiParaActivityPiquete(nomeUsuario, nomePropriedade);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Insira o nome da propriedade", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void vaiParaActivityPiquete(String nomeUsuario, String nomePropriedade){
        Intent i = new Intent(PropriedadeActivity.this, PiqueteActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        i.putExtra("nome_propriedade", nomePropriedade);
        i.putExtra("regiao", regiao);
        startActivityForResult(i, CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY);
    }

    /**
     * Método responsável por retornar valores de cálculo do banco dependendo da escolha do usuário (região norte ou sul).
     */
    private void escolherRegiao() {
        ArrayList<String> regiaoPiquete = new ArrayList<>();
        regiaoPiquete.add("cfa");
        regiaoPiquete.add("cfb");

        Spinner spinnerRegiaoPiquete = findViewById(R.id.sp_regiao);
        ArrayAdapter<String> spinnerRegiaoAdapter = new ArrayAdapter<>(PropriedadeActivity.this, android.R.layout.simple_spinner_item, regiaoPiquete);
        spinnerRegiaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegiaoPiquete.setAdapter(spinnerRegiaoAdapter);
    }

    /**
     * Método utilizado para enviar de volta para a IndexActivity o nome do usuário caso ele clique no botão voltar.
     */
    public void enviaResposta(){
        Intent intent = new Intent();
        intent.putExtra("nome_usuario", nomeUsuario);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Método que volta para activity anterior(Index), pela seta de voltar em cima da tela.
     * @param item Representa o botão do smartphone que foi clicado.
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

        if (requestCode == CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                //Informações Animais.
                ArrayList<Animais> listaAnimais = data.getParcelableArrayListExtra("listaAnimais");
                ArrayList<Double> listaTotalUAHA = (ArrayList<Double>) data.getSerializableExtra("listaTotaisUAHA");
                nomeUsuario = data.getStringExtra("nome_usuario");
                int qtdeAnimais = data.getIntExtra("qtdeAnimal", 0);
                double area = data.getDoubleExtra("area", 1.0);

                //Informações Piquetes
                ArrayList<Piquete> listaPiquete = (ArrayList<Piquete>) data.getSerializableExtra("listaPiquetes");
                ArrayList<Double> listaTotaisMes = (ArrayList<Double>) data.getSerializableExtra("listaTotaisMes");
                ArrayList<Double> listaTotaisEstacoes = (ArrayList<Double>) data.getSerializableExtra("listaTotaisEstacoes");

                /*for(int i=0; i<listaAnimais.size(); i++){
                    Log.i("listaAnimais", listaAnimais.get(i).getCategoria() + " "
                            + listaAnimais.get(i).getConsumo() + " "
                            + listaAnimais.get(i).getNumAnimais() + " "
                            + listaAnimais.get(i).getEntradaMes() + " "
                            + listaAnimais.get(i).getPesoInicial() + " "
                            + listaAnimais.get(i).getPesoFinal() + " "
                            + listaAnimais.get(i).getPesoGanhoVer() + " "
                            + listaAnimais.get(i).getPesoGanhoOut() + " "
                            + listaAnimais.get(i).getPesoGanhoInv() + " "
                            + listaAnimais.get(i).getPesoGanhoPrim() + " "
                            + Arrays.toString(listaAnimais.get(i).getMeses()));
                }
                Log.i("listaTotalUAHA", String.valueOf(listaTotalUAHA));

                for(int i=0; i<listaPiquete.size(); i++){
                    Log.i("listaPiquetes", listaPiquete.get(i).getTipo() + " "
                            + listaPiquete.get(i).getCondicao() + " "
                            + listaPiquete.get(i).getArea() + " "
                            + listaPiquete.get(i).getProdEstimada() + " "
                            + Arrays.toString(listaPiquete.get(i).getMeses()) + " "
                            + listaPiquete.get(i).getTotal());
                }
                Log.i("listaTotaisMes", String.valueOf(listaTotaisMes));
                Log.i("listaTotaisEstacoes", String.valueOf(listaTotaisEstacoes));*/

                //Valor para representar que não encontrou o usuário com o nome recebido por parâmetro (-1).
                int usuarioId = MainActivity.bancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
                if(usuarioId != -1){
                    Propriedade p = new Propriedade(nomePropriedade, regiao, area, qtdeAnimais, listaPiquete, listaAnimais);
                    MainActivity.bancoDeDados.propriedadeDAO.inserirPropriedade(p, usuarioId);

                    int propriedadeId = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(nomePropriedade);
                    if(propriedadeId != -1){
                        for(int i=0; i<listaPiquete.size(); i++){
                            MainActivity.bancoDeDados.piqueteDAO.inserirPiquete(listaPiquete.get(i), propriedadeId, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
                        }

                        MainActivity.bancoDeDados.totalPiqueteMesDAO.inserirTotalMes(listaTotaisMes, propriedadeId, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                        MainActivity.bancoDeDados.totalPiqueteEstacaoDAO.inserirTotalEstacao(listaTotaisEstacoes, propriedadeId, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);

                        for(int i=0; i<listaAnimais.size(); i++){
                            MainActivity.bancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), propriedadeId, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
                        }
                        MainActivity.bancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, propriedadeId, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("nome_usuario", nomeUsuario);
                setResult(RESULT_OK, intent);

                finish();
            }
        }
    }

    //Chamado no arquivo de layout
    public void clicarVoltarPropriedade(View view) {
        finish();
    }
}

