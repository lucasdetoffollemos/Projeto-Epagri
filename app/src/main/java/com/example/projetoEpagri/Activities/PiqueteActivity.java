package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.DadosSul;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;

import java.util.ArrayList;


public class PiqueteActivity extends AppCompatActivity{
    private  Button bt_adicionar_linha, bt_remover_linha;
    public int i=-1;
    private  TableRow linha_tabela;
    private TableLayout table_layout;
    private DadosSulDAO dadosSulDAO;

    //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
    private String tipo, condicao, areaS;
    private double areaD;
    private Spinner sp_tipo, sp_condicao;
    private EditText et_area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        dadosSulDAO = new DadosSulDAO(PiqueteActivity.this);

        table_layout = (TableLayout) findViewById(R.id.table_layout);


        bt_adicionar_linha = findViewById(R.id.bt_adicionar_linha);
        //Quando clicado no botao de mais, é acionado está funçao.
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarLinhaTabela();
                i++;
                //Toast.makeText(getApplicationContext(), "OI", Toast.LENGTH_SHORT).show();
            }
        });

        //Quando clicado no botao de menos, é acionado está funçao, que tem como objetivo excluir cada linha da tabela.
        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(i));
                i--;
                //Toast.makeText(getApplicationContext(), "tCHAU", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método responsável por adicionar uma linha na tabela oferta atual e configurar o adapter dos spinners.
     */
    private void adicionarLinhaTabela(){
        //Infla a linha para a tabela
        linha_tabela = (TableRow) View.inflate(PiqueteActivity.this, R.layout.tabela_oferta_atual_linha, null);

        // Array que armazena os tipos de piquetes, vindos do arquivo DadosSulDAO.java.
        ArrayList<String> tipoPiquete = dadosSulDAO.getTiposPastagem();
        //Localiza o spinner tipo no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinnerTipoPiquete);
        //Cria um ArrayAdpter usando o array de string com os tipos armazenados no banco de dados.
        ArrayAdapter<String> spinnerTipoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, tipoPiquete);
        spinnerTipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPiquete.setAdapter(spinnerTipoAdapter);

        //Criando um array de String para as condiçoes de pastagem.
        ArrayList<String> condicaoPiquete = new ArrayList<>();
        condicaoPiquete.add("Degradada");
        condicaoPiquete.add("Média");
        condicaoPiquete.add("Ótima");

        //Localiza o spinner condicao no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerCondicaoPiquete = linha_tabela.findViewById(R.id.spinnerCondPiquete);
        //Cria um ArrayAdpter usando o array de string com condicoes "degradada", "média" e "ótima". //Cria um ArrayAdapter que pega o Array de string "condicaoPiquete" e transforma em um spinner.
        ArrayAdapter<String> spinnerCondicaoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, condicaoPiquete);
        spinnerCondicaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicaoPiquete.setAdapter(spinnerCondicaoAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(i);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);

        identificaElementosDaLinha(linha_tabela);
    }






    /**
     * Identifica os elementos dentro da TableRow que foi inflada e chama o método de calcular quando algum valor
     * é escolhido nos spinners ou texto digitado no campo área.
     * @param linha
     */
    private void identificaElementosDaLinha(final TableRow linha) {
        final Spinner spinnerTipo = (Spinner) linha.getChildAt(0);
        final Spinner spinnerCondicao = (Spinner) linha.getChildAt(1);
        final EditText editTextArea = (EditText) linha.getChildAt(2);



        //Quando o spinner TIPO for clicado, a funçao ira convereter o spinner para string, e logo depois ira chamar a funçao calcular().
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;
                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }

                calcular(linha, tipo, condicao, areaD);

            }




            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        //Quando o spinner CONDIÇAO for clicado, a funçao ira converter o spinner para string, e logo depeois chamar a funçao calcular()
        spinnerCondicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;

                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }

                calcular(linha, tipo, condicao, areaD);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Quando o usuário digitar um número no campo Área, esta funçao ira converter o valor recebido por ele, e será chamadoa funçao calcular().
        editTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;
                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }

                calcular(linha, tipo, condicao, areaD);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }



    /*
        primeira linha = -1
        segunda linha = 0
        terceira linha = 1
        Dentro da linha
            spinnerTipo = getchildAt(0)
            spinnerCondicao = getchildAt(1)
            campoArea = getchildAt(2)

    */

    /**
     * Método responsavel, por pegar os valores dos spinners, TIPO e CONDIÇÃO, e pegar o editText ÁREA, e realizar os cáculos de pastagem mensal. Também setar uma tag para cada elemento da linha.
     * @param linha
     * @param tipoPastagem
     * @param condicao
     * @param area
     */
    public void calcular(final TableRow linha, String tipoPastagem, String condicao, double area) {

//        Toast.makeText(this, "Tipo: " + tipoPastagem + " Cond: " + condicao + " Área: " + area, Toast.LENGTH_SHORT).show();
        //Log.i("CALCULAR", "Tipo: " + tipoPastagem + " Cond: " + condicao + " Área: " + area);

        //TO DO.
        //Exemplo

        TextView janeiro = (TextView) linha.getChildAt(3);
        //formula = (condicao * area) / 5
        double resultado = (dadosSulDAO.getCondicao(tipoPastagem, condicao) * area) / 5;
        janeiro.setText(String.valueOf(resultado));



//        String texto = linha.getTag().toString();
//        Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
    }
}
