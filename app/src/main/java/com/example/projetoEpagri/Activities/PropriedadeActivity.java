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
import com.example.projetoEpagri.Classes.BancoDeDados;
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
    private final int CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY = 0;

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
     * @return true quando algum botão foi clicado.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            enviaResposta();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Método responsável por lidar com as respostas enviadas da activity Piquete.
     * @param requestCode Representa o código da activity que está esperando a resposta.
     * @param resultCode Representa o código da activity que enviou a resposta.
     * @param data Representa a informação enviada como resposta.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_REQUISICAO_PROPRIEDADE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                nomeUsuario = data.getStringExtra("nome_usuario");
                int qtdeAnimais = data.getIntExtra("qtdeAnimal", 0);
                double area = data.getDoubleExtra("area", 1.0);

                //Informações Piquetes
                @SuppressWarnings("unchecked")
                ArrayList<Piquete> listaPiquete = (ArrayList<Piquete>) data.getSerializableExtra("listaPiquetes");
                @SuppressWarnings("unchecked")
                ArrayList<Double> listaTotaisMes = (ArrayList<Double>) data.getSerializableExtra("listaTotaisMes");
                @SuppressWarnings("unchecked")
                ArrayList<Double> listaTotaisEstacoes = (ArrayList<Double>) data.getSerializableExtra("listaTotaisEstacoes");

                //Informações Animais.
                ArrayList<Animais> listaAnimais = data.getParcelableArrayListExtra("listaAnimais");
                @SuppressWarnings("unchecked")
                ArrayList<Double> listaTotalUAHA = (ArrayList<Double>) data.getSerializableExtra("listaTotaisUAHA");

                //Valor para representar que não encontrou o usuário com o nome recebido por parâmetro (-1).
                int usuarioId = BancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
                if(usuarioId != -1){
                    Propriedade p = new Propriedade(nomePropriedade, regiao, area, qtdeAnimais, listaPiquete, listaAnimais);
                    BancoDeDados.propriedadeDAO.inserirPropriedade(p, usuarioId);

                    int propriedadeId = BancoDeDados.propriedadeDAO.getPropriedadeId(nomePropriedade);
                    if(propriedadeId != -1){
                        if(listaPiquete != null){
                            for(int i=0; i<listaPiquete.size(); i++){
                                BancoDeDados.piqueteDAO.inserirPiquete(listaPiquete.get(i), propriedadeId, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
                            }
                        }

                        if(listaTotaisMes != null){
                            BancoDeDados.totalPiqueteMesDAO.inserirTotalMes(listaTotaisMes, propriedadeId, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                        }

                        if(listaTotaisEstacoes != null){
                            BancoDeDados.totalPiqueteEstacaoDAO.inserirTotalEstacao(listaTotaisEstacoes, propriedadeId, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
                        }

                        if(listaAnimais != null){
                            for(int i=0; i<listaAnimais.size(); i++){
                                BancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), propriedadeId, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
                            }
                        }

                        if(listaTotalUAHA != null){
                            BancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, propriedadeId, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);
                        }
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

