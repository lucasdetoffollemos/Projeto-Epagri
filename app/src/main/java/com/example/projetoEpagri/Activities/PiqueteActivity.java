package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PiqueteActivity extends AppCompatActivity{
    private  Button bt_adicionar_linha, bt_remover_linha, bt_proximo_passo;
    public int posicaoLinhaTabela=-1, numeroDeLinhas=0;
    private TableRow linha_tabela;
    private TableLayout table_layout;
    private double producaoEstimadaD, areaTotal;
    private int intTotalTonelada;
    private ArrayList<double[]> matrizMeses;       //matriz que armazena os valores calculados de todas as linhas para todos os meses.
    private ArrayList<Double> listaTotaisEstacoes;      //lista de 4 posiçõe para guardar os valores das estações.
    private ArrayList<Double> listaDeAreas;             //lista com as áreas de todas as linhas.
    private ArrayList<TextView> listaTextViewTotaisMes; //lista com os textviews dos totais dos 12 meses.

    private static final int CODIGO_REQUISICAO_ANIMAIS_ACTIVITY = 0;

    //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
    private String tipo, condicao, areaS;
    private double areaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        inicializa();
        setListeners();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        listaDeAreas = new ArrayList<Double>();
        matrizMeses = new ArrayList<double[]>();
        listaTotaisEstacoes = new ArrayList<Double>();
        listaTextViewTotaisMes = new ArrayList<TextView>();

        TextView jan = findViewById(R.id.tv_AreaTotalMesJan);
        TextView fev = findViewById(R.id.tv_AreaTotalMesFev);
        TextView mar = findViewById(R.id.tv_AreaTotalMesMar);
        TextView abr = findViewById(R.id.tv_AreaTotalMesAbr);
        TextView mai = findViewById(R.id.tv_AreaTotalMesMai);
        TextView jun = findViewById(R.id.tv_AreaTotalMesJun);
        TextView jul = findViewById(R.id.tv_AreaTotalMesJul);
        TextView ago = findViewById(R.id.tv_AreaTotalMesAgo);
        TextView set = findViewById(R.id.tv_AreaTotalMesSet);
        TextView out = findViewById(R.id.tv_AreaTotalMesOut);
        TextView nov = findViewById(R.id.tv_AreaTotalMesNov);
        TextView dez = findViewById(R.id.tv_AreaTotalMesDez);

        listaTextViewTotaisMes.add(jan);
        listaTextViewTotaisMes.add(fev);
        listaTextViewTotaisMes.add(mar);
        listaTextViewTotaisMes.add(abr);
        listaTextViewTotaisMes.add(mai);
        listaTextViewTotaisMes.add(jun);
        listaTextViewTotaisMes.add(jul);
        listaTextViewTotaisMes.add(ago);
        listaTextViewTotaisMes.add(set);
        listaTextViewTotaisMes.add(out);
        listaTextViewTotaisMes.add(nov);
        listaTextViewTotaisMes.add(dez);

        table_layout = (TableLayout) findViewById(R.id.tableLayout_tabelaPiquete);
        bt_adicionar_linha = findViewById(R.id.bt_adicionarLinha);
        bt_remover_linha = findViewById(R.id.bt_removerLinha);
        bt_proximo_passo = findViewById(R.id.bt_proximoPasso);
    }

    /**
     * Método utilizado para setar os listener dos botões.
     */
    public void setListeners(){
        //Quando clicado no botao de mais, é acionado está funçao.
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Infla a linha para a tabela
                        linha_tabela = (TableRow) View.inflate(PiqueteActivity.this, R.layout.tabela_oferta_atual_linha, null);
                        adicionarLinhaTabela(linha_tabela);
                        setListenersDinamicos(linha_tabela);

                        posicaoLinhaTabela++; //O indica a posição da linha dentro do TableView (primeira posição = -1)
                        numeroDeLinhas++;

                        if(listaDeAreas.size() < numeroDeLinhas){
                            listaDeAreas.add(0.0);
                        }
            }
        });

        //Quando clicado no botao de menos, é acionado está funçao, que tem como objetivo excluir cada linha da tabela.
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(posicaoLinhaTabela));
                posicaoLinhaTabela--;

                if(posicaoLinhaTabela >= -1){
                    listaDeAreas.remove(numeroDeLinhas-1);
                    matrizMeses.remove(numeroDeLinhas-1);

                    //Limpa a matriz com valores 0 para remover os cálculo dos totais.
                    //Se não eles continuam aparecendo mesmo após remover todas as linhas.
                    //Isso ocorre pois no método calculaTotais os valores dos textviews são setados em um while com condição
                    //matrizMeses.size() > 0.
                    double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
                    if(matrizMeses.size() < numeroDeLinhas){
                        matrizMeses.add(startArray);
                    }

                    numeroDeLinhas--;
                }
                else{
                    listaTotaisEstacoes.set(0, 0.0);
                    listaTotaisEstacoes.set(1, 0.0);
                    listaTotaisEstacoes.set(2, 0.0);
                    listaTotaisEstacoes.set(3, 0.0);

                    posicaoLinhaTabela = -1;
                }
                calculaTotais();
            }
        });


        bt_proximo_passo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaAnimaisActivity();
            }
        });

        //Método que tem o spinner para selecionar qual regiao o usário deseja
        escolherRegiao();
    }

    /**
     * Método responsável por retornar valores de cálculo do banco dependendo da escolha do usuário (região norte ou sul).
     */
    private void escolherRegiao() {
        ArrayList<String> regiaoPiquete = new ArrayList<>();
        regiaoPiquete.add("Sul");
        regiaoPiquete.add("Norte");

        Spinner spinnerRegiaoPiquete = findViewById(R.id.spinnerRegiao);
        ArrayAdapter<String> spinnerRegiaoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, regiaoPiquete);
        spinnerRegiaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegiaoPiquete.setAdapter(spinnerRegiaoAdapter);
    }

    /**
     * Método responsável por adicionar uma linha na tabela oferta atual e configurar o adapter dos spinners.
     */
    private void adicionarLinhaTabela(TableRow linha_tabela){
        // Array que armazena os tipos de piquetes, vindos do arquivo DadosSulDAO.java.
        ArrayList<String> tipoPiquete = LoginActivity.bancoDeDados.dadosSulDAO.getTiposPastagem();
        //Localiza o spinner tipo no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinner_tipoPiquete);
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
        Spinner spinnerCondicaoPiquete = linha_tabela.findViewById(R.id.spinner_condPiquete);
        //Cria um ArrayAdpter usando o array de string com condicoes "degradada", "média" e "ótima". //Cria um ArrayAdapter que pega o Array de string "condicaoPiquete" e transforma em um spinner.
        ArrayAdapter<String> spinnerCondicaoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, condicaoPiquete);
        spinnerCondicaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicaoPiquete.setAdapter(spinnerCondicaoAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);
    }

     /* primeira linha = -1
        segunda linha = 0
        terceira linha = 1
        Dentro da linha
            spinnerTipo = getchildAt(0)
            spinnerCondicao = getchildAt(1)
            campoArea = getchildAt(2) */

    /**
     * Identifica os elementos dentro da TableRow que foi inflada e chama o método de calcular quando algum valor
     * é escolhido nos spinners ou texto digitado no campo área.
     * @param linha_tabela
     */
    private void setListenersDinamicos(final TableRow linha_tabela) {
        final Spinner spinnerTipo = (Spinner) linha_tabela.getChildAt(0);
        final Spinner spinnerCondicao = (Spinner) linha_tabela.getChildAt(1);
        final EditText editTextArea = (EditText) linha_tabela.getChildAt(2);

        //Quando o spinner TIPO for clicado, a funçao ira convereter o spinner para string, e logo depois ira chamar a funçao calcular().
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lerValoresLinhaECalcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Quando o spinner CONDIÇAO for clicado, a funçao ira converter o spinner para string, e logo depeois chamar a funçao calcular()
        spinnerCondicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lerValoresLinhaECalcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
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
                lerValoresLinhaECalcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Método utilizado para ler os valores preenchidos pelo usuário (spinners e área) e fazer os cálculos de produção
     * estimada, meses e total.
     * @param linha_tabela
     * @param spinnerTipo
     * @param spinnerCondicao
     * @param editTextArea
     */
    public void lerValoresLinhaECalcular(final TableRow linha_tabela, Spinner spinnerTipo, Spinner spinnerCondicao, EditText editTextArea){
        tipo = spinnerTipo.getSelectedItem().toString();
        condicao = spinnerCondicao.getSelectedItem().toString();
        areaS = editTextArea.getText().toString();

        int posicao = Integer.parseInt(linha_tabela.getTag().toString())+1;

        //Adiciona as áreas digitadas na lista de áreas.
        if(areaS.length() > 0) {
            areaD = Double.parseDouble(areaS);
        }
        else{
            areaD = 0;
        }
        listaDeAreas.set(posicao, areaD);

        //Cálcula a producão estimada.
        calculaProducaoEstimada(linha_tabela, tipo, condicao, areaD);

        //Cálcula a produção para cada mês. E faz a soma de todos os meses
        double [] arrayMesesProd = new double[12];
        double totalToneladaAnual = 0;
        for(int i=1; i<=12; i++){
            arrayMesesProd[i-1] = calculaMes(linha_tabela, tipo, condicao, areaD, i);
            totalToneladaAnual = totalToneladaAnual + arrayMesesProd[i-1];
        }

        //Mostra o Total (direita).
        TextView total = (TextView) linha_tabela.getChildAt(16);
        intTotalTonelada = Integer.valueOf((int) totalToneladaAnual);
        total.setText(String.valueOf(intTotalTonelada));

        /*Mapeia os dados para a matriz.
        Salva os valores calculados para cada mes (em todas as linhas) em uma matriz.
        Adiciona inicialmente um array de zeros na lista pois o método é chamado antes do cálculo ser realizado.
        Ao clicar no botão de adicionar linha, o método já é chamado e adiciona-se o vetor de zeros.
        Ao preencher os campos, chama-se novamente, atualizando o vetor de zeros.*/
        double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
        if(matrizMeses.size() < numeroDeLinhas){
            matrizMeses.add(startArray);
        }
        else{
            matrizMeses.set(posicao, arrayMesesProd);
        }

        calculaTotais();
    }

    /**
     * Método para calcula a produção estimada.
     */
    public void calculaProducaoEstimada(final TableRow linha_tabela, String tipoPastagem, String condicao, double area){
        DecimalFormat doisDecimais = new DecimalFormat("#.##");
        TextView tv_prod = (TextView) linha_tabela.getChildAt(3); //posição da coluna produção estimada.
        producaoEstimadaD = (LoginActivity.bancoDeDados.dadosSulDAO.getCondicao(tipoPastagem, condicao)) * area;
        String producaoEstimada = doisDecimais.format(producaoEstimadaD);
        tv_prod.setText(String.valueOf(producaoEstimada));
    }

    /**
     * Método para calcular a produção em cada um dos meses.
     * @param linha_tabela
     * @param tipoPastagem
     * @param condicao
     * @param area
     * @param mes
     */
    public double calculaMes(final TableRow linha_tabela, String tipoPastagem, String condicao, double area, int mes){
        //Arredonda o cálculo para 2 decimais.
        DecimalFormat doisDecimais = new DecimalFormat("#.##");
        Double aproveitamento = 0.60;

        //Janeiro está na posição 4, por isso mes+3.
        TextView tv_mes = (TextView) linha_tabela.getChildAt(mes+3);

        double valor = (float)LoginActivity.bancoDeDados.dadosSulDAO.getMeses(mes, tipoPastagem)/100;
        valor = ((LoginActivity.bancoDeDados.dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * valor * area);
        String resultado = doisDecimais.format(valor);

        if(valor != 0){
            tv_mes.setText(String.valueOf(resultado));
        }
        else {
            tv_mes.setText(" ");
        }

        return valor;
    }

    /**
     * Método para calcular os totais de cada coluna (mês).
     */
    public void calculaTotais(){
        //Arredonda o cálculo para 2 decimais.
        DecimalFormat doisDecimais = new DecimalFormat("#.##");

        //Calcula a área total.
        areaTotal = 0.0;
        for(int i=0; i<listaDeAreas.size(); i++){
            areaTotal = areaTotal + listaDeAreas.get(i);
        }
        TextView area = findViewById(R.id.tv_AreaTotalNumHa);
        area.setText(doisDecimais.format(areaTotal));

        //Inicializa a lista de totais das estações. Somente entra no if na primeira execução (quando o botão de + é clicado
        //e o numero de linhas é maior que o tamanho da lista.
        //Adiciona-se valores zero para depois serem atualizados conforme os cálculos vão sendo realizados.
        if(listaTotaisEstacoes.size() < numeroDeLinhas){
            listaTotaisEstacoes.add(0.0); //Verao
            listaTotaisEstacoes.add(0.0); //Outono
            listaTotaisEstacoes.add(0.0); //Inverno
            listaTotaisEstacoes.add(0.0); //Primavera
        }
        else{  //evita que o valor já calculado seja somado novamente, pois o método é chamado uma vez para cada spinner (quando adiciona-se uma linha).
            listaTotaisEstacoes.set(0, 0.0);
            listaTotaisEstacoes.set(1, 0.0);
            listaTotaisEstacoes.set(2, 0.0);
            listaTotaisEstacoes.set(3, 0.0);
        }

        double total = 0.0;
        int i=0, j=0;

        //Calcula o total para cada mês percorrendo a matriz de valores.
        //i = linha e j = coluna.
        while(i<matrizMeses.size()){
            total = total + matrizMeses.get(i)[j];

            //Ao chegar na última linha, retorna para a primeira e passa para a proxima coluna.
            if((i+1) == matrizMeses.size()){
                i=0;

                //Define o texto nos textviews de acordo com cada mês.
                switch (j){
                    case 0:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 1:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 2:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 3:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 4:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 5:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 6:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 7:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 8:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 9:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 10:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                    case 11:
                        listaTextViewTotaisMes.get(j).setText(doisDecimais.format((total)));
                        break;
                }

                //Enquanto não chega no último mês, vai armazendo os valores calculados na lista de totais da estação.
                //Ao chegar no final, atualiza o texto dos textviews.
                if(j+1 < 12) {
                    //Verao
                    if(( j == 0 || j == 1)){
                        listaTotaisEstacoes.set(0, listaTotaisEstacoes.get(0) + total);
                    }

                    //Outono
                    if(j == 2 || j == 3 || j == 4){
                        listaTotaisEstacoes.set(1, listaTotaisEstacoes.get(1) + total);
                    }

                    //Inverno
                    if(j == 5 || j == 6 || j == 7){
                        listaTotaisEstacoes.set(2, listaTotaisEstacoes.get(2) + total);
                    }

                    //Primavera
                    if(j == 8 || j == 9 || j == 10){
                        listaTotaisEstacoes.set(3, listaTotaisEstacoes.get(3) + total);
                    }

                    j++;
                    total = 0.0;
                }
                else{
                    //Esse cálculo fica aqui pois quando o j=11 ele não entra no if (acima).
                    if(j == 11){
                        listaTotaisEstacoes.set(0, listaTotaisEstacoes.get(0) + total);
                    }

                    TextView totalVer = findViewById(R.id.tv_AreaTotalVer);
                    TextView totalOut = findViewById(R.id.tv_AreaTotalOut);
                    TextView totalInv = findViewById(R.id.tv_AreaTotalInve);
                    TextView totalPrim = findViewById(R.id.tv_AreaTotalPrim);

                    totalVer.setText(doisDecimais.format(listaTotaisEstacoes.get(0)));
                    totalOut.setText(doisDecimais.format(listaTotaisEstacoes.get(1)));
                    totalInv.setText(doisDecimais.format(listaTotaisEstacoes.get(2)));
                    totalPrim.setText(doisDecimais.format(listaTotaisEstacoes.get(3)));
                    break;
                }
            }
            else{
                i++;
            }
        }
    }

    /**
     * Método chamado toda vez que o botão Próximo Passo é clicado, e tem como objetivo levar o usário para outra activity e mandar um array de totais/mês. (Obs: método é acionado, no onclick do Botao Proximo Passo, que se encontra em activity_piquete.xml)
     */
    public void irParaAnimaisActivity() {
        Intent i=new Intent(getApplicationContext(), AnimaisActivity.class);
        i.putExtra("areaTotal", areaTotal);
        startActivityForResult(i, CODIGO_REQUISICAO_ANIMAIS_ACTIVITY);
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_REQUISICAO_ANIMAIS_ACTIVITY) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                ArrayList<Animais> listaAnimais = data.getParcelableArrayListExtra("listaAnimais");
                ArrayList<Double> listaTotalUAHA = (ArrayList<Double>) data.getSerializableExtra("totais");

                /*for(int i=0; i<listaAnimal.size(); i++){
                    Log.i("Animais", listaAnimal.get(i).getCategoria() + " " + listaAnimal.get(i).getConsumo() + " " + listaAnimal.get(i).getNumAnimais() + " " + listaAnimal.get(i).getEntradaMes() + " " + listaAnimal.get(i).getPesoInicial()+ " " + listaAnimal.get(i).getPesoFinal() + " " + listaAnimal.get(i).getPesoGanhoVer() + " " + listaAnimal.get(i).getPesoGanhoOut() + " " + listaAnimal.get(i).getPesoGanhoInv() + " " + listaAnimal.get(i).getPesoGanhoPrim() + " " + Arrays.toString(listaAnimal.get(i).getMeses()));
                }
                Log.i("Totais", String.valueOf(totais));*/

                Intent intent = new Intent();
                intent.putExtra("listaAnimais", listaAnimais);
                intent.putExtra("totais", listaTotalUAHA);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
