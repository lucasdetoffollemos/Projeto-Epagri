package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PiqueteActivity extends AppCompatActivity{
    private Button bt_adicionar_linha, bt_remover_linha, bt_proximo_passo;
    private TableLayout table_layout;
    private TableRow linha_tabela;
    public int posicaoLinhaTabela=-1, numeroDeLinhas=0;

    private double producaoEstimadaD, areaTotal;
    private ArrayList<Double> listaDeAreas;             //lista com as áreas de todas as linhas.
    private ArrayList<double[]> matrizMeses;            //matriz que armazena os valores calculados de todas as linhas para todos os meses.
    private ArrayList<Double> listaTotaisMes;
    private ArrayList<Double> listaTotaisEstacoes;      //lista de 4 posiçõe para guardar os valores das estações.
    private ArrayList<Piquete> listaPiquetes;
    private ArrayList<TextView> listaTextViewTotaisMes; //lista com os textviews dos totais dos 12 meses.

    private String nomeUsuario;
    private DecimalFormat doisDecimais;
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
        listaDeAreas = new ArrayList<>();
        matrizMeses = new ArrayList<>();
        listaTotaisMes = new ArrayList<>();
        listaTotaisEstacoes = new ArrayList<>();
        listaPiquetes = new ArrayList<>();
        listaTextViewTotaisMes = new ArrayList<>();

        if(listaTotaisMes.isEmpty()){
            for(int i=0; i<12; i++){
                listaTotaisMes.add(0.0);
            }
        }

        if(listaTotaisEstacoes.isEmpty() ){
            listaTotaisEstacoes.add(0.0); //Verao
            listaTotaisEstacoes.add(0.0); //Outono
            listaTotaisEstacoes.add(0.0); //Inverno
            listaTotaisEstacoes.add(0.0); //Primavera
        }

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

        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");
        //Toast.makeText(PiqueteActivity.this, nomeUsuario, Toast.LENGTH_SHORT).show();

        doisDecimais = new DecimalFormat("#.##");
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
                criarLinha(linha_tabela);
                setListenersLinha(linha_tabela);

                posicaoLinhaTabela++; //O indica a posição da linha dentro do TableView (primeira posição = -1)
                numeroDeLinhas++;

                //Adiciona elementos sem valor nas listas para garantir que tenham o mesmo tamanho que o número de linhas.
                //Isso permite controlar o tamanho das listas no momento que as linhas são adicionadas ou removidas.
                if (listaDeAreas.size() < numeroDeLinhas) {
                    listaDeAreas.add(0.0);
                }

                double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
                if(matrizMeses.size() < numeroDeLinhas){
                    matrizMeses.add(startArray);
                }

                if (listaPiquetes.size() < numeroDeLinhas) {
                    Piquete temp = new Piquete();
                    listaPiquetes.add(temp);
                }
            }
        });

        //Quando clicado no botao de menos, é acionado está funçao, que tem como objetivo excluir cada linha da tabela.
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(posicaoLinhaTabela));

                if(numeroDeLinhas > 0){
                    listaDeAreas.remove(numeroDeLinhas-1);
                    matrizMeses.remove(numeroDeLinhas-1);
                    listaPiquetes.remove(numeroDeLinhas-1);

                    posicaoLinhaTabela--;
                    numeroDeLinhas--;

                    if(numeroDeLinhas == 0){
                        for(int i=0; i<listaTotaisMes.size(); i++){
                            listaTotaisMes.set(i, 0.0);
                        }

                        for(int i=0; i<listaTotaisEstacoes.size(); i++){
                            listaTotaisEstacoes.set(i, 0.0);
                        }
                    }
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
    }

    /**
     * Método responsável por adicionar uma linha na tabela oferta atual e configurar o adapter dos spinners.
     */
    private void criarLinha(TableRow linha_tabela){
        // Array que armazena os tipos de piquetes, vindos do arquivo DadosSulDAO.java.
        ArrayList<String> tipoPiquete = MainActivity.bancoDeDados.dadosSulDAO.getTiposPastagem();
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
    private void setListenersLinha(final TableRow linha_tabela) {
        final Spinner spinnerTipo = (Spinner) linha_tabela.getChildAt(0);
        final Spinner spinnerCondicao = (Spinner) linha_tabela.getChildAt(1);
        final EditText editTextArea = (EditText) linha_tabela.getChildAt(2);

        //Quando o spinner TIPO for clicado, a funçao ira convereter o spinner para string, e logo depois ira chamar a funçao calcular().
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Quando o spinner CONDIÇAO for clicado, a funçao ira converter o spinner para string, e logo depeois chamar a funçao calcular()
        spinnerCondicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
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
                calcular(linha_tabela, spinnerTipo, spinnerCondicao, editTextArea);
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
    public void calcular(final TableRow linha_tabela, Spinner spinnerTipo, Spinner spinnerCondicao, EditText editTextArea){
        this.tipo = spinnerTipo.getSelectedItem().toString();
        this.condicao = spinnerCondicao.getSelectedItem().toString();
        this.areaS = editTextArea.getText().toString();

        int posicao = Integer.parseInt(linha_tabela.getTag().toString())+1;

        //Adiciona as áreas digitadas na lista de áreas.
        if(this.areaS.length() > 0) {
            this.areaD = Double.parseDouble(this.areaS);
        }
        else{
            this.areaD = 0;
        }
        this.listaDeAreas.set(posicao, this.areaD);

        //Cálcula a producão estimada.
        calculaProducaoEstimada(linha_tabela, this.tipo, this.condicao, this.areaD);

        //Cálcula a produção para cada mês. E faz a soma de todos os meses
        double [] arrayMesesProd = new double[12];
        double totalToneladaAnual = 0;
        for(int i=1; i<=12; i++){
            arrayMesesProd[i-1] = calculaMes(linha_tabela, this.tipo, this.condicao, this.areaD, i);
            totalToneladaAnual = totalToneladaAnual + arrayMesesProd[i-1];
        }

        /*Mapeia os dados para a matriz.
        Salva os valores calculados para cada mes (em todas as linhas) em uma matriz.
        */
        if(matrizMeses.size() >= numeroDeLinhas){
            matrizMeses.set(posicao, arrayMesesProd);
        }

        //Mostra o Total (direita).
        TextView total = (TextView) linha_tabela.getChildAt(16);
        int intTotalTonelada = Integer.valueOf((int) totalToneladaAnual);
        total.setText(String.valueOf(intTotalTonelada));

        calculaTotais();

        if(listaPiquetes.size() >= numeroDeLinhas){
            Piquete p = new Piquete(tipo, condicao, areaD, producaoEstimadaD, arrayMesesProd, intTotalTonelada);
            listaPiquetes.set(posicao, p);
        }
    }

    /**
     * Método para calcula a produção estimada.
     */
    public void calculaProducaoEstimada(final TableRow linha_tabela, String tipoPastagem, String condicao, double area){
        TextView tv_prod = (TextView) linha_tabela.getChildAt(3); //posição da coluna produção estimada.
        this.producaoEstimadaD = (MainActivity.bancoDeDados.dadosSulDAO.getCondicao(tipoPastagem, condicao)) * area;
        String producaoEstimada = this.doisDecimais.format(this.producaoEstimadaD);
        tv_prod.setText(producaoEstimada);
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
        Double aproveitamento = 0.60;

        //Janeiro está na posição 4, por isso mes+3.
        TextView tv_mes = (TextView) linha_tabela.getChildAt(mes+3);

        double valor = (float) MainActivity.bancoDeDados.dadosSulDAO.getMeses(mes, tipoPastagem)/100;
        valor = ((MainActivity.bancoDeDados.dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * valor * area);
        String resultado = this.doisDecimais.format(valor);

        if(valor != 0){
            tv_mes.setText(resultado);
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
        //Calcula a área total.
        this.areaTotal = 0.0;
        for (int i = 0; i < this.listaDeAreas.size(); i++) {
            this.areaTotal = this.areaTotal + this.listaDeAreas.get(i);
        }
        TextView area = findViewById(R.id.tv_AreaTotalNumHa);
        area.setText(this.doisDecimais.format(this.areaTotal));

        double total = 0.0;
        int i = 0, j = 0;

        //Calcula o total para cada mês percorrendo a matriz de valores.
        //i = linha e j = coluna.
        while (i < this.matrizMeses.size()) {
            total = total + this.matrizMeses.get(i)[j];

            //Ao chegar na última linha, retorna para a primeira e passa para a proxima coluna.
            if ((i + 1) == this.matrizMeses.size()) {
                i = 0;

                //Enquanto não chega no último mês, vai armazendo os valores calculados na lista de totais da estação.
                //Ao chegar no final, atualiza o texto dos textviews.
                if (j + 1 < 12) {
                    //Verao
                    if ((j == 0 || j == 1)) {
                        this.listaTotaisEstacoes.set(0, this.listaTotaisEstacoes.get(0) + total);
                    }

                    //Outono
                    if (j == 2 || j == 3 || j == 4) {
                        this.listaTotaisEstacoes.set(1, this.listaTotaisEstacoes.get(1) + total);
                    }

                    //Inverno
                    if (j == 5 || j == 6 || j == 7) {
                        this.listaTotaisEstacoes.set(2, this.listaTotaisEstacoes.get(2) + total);
                    }

                    //Primavera
                    if (j == 8 || j == 9 || j == 10) {
                        this.listaTotaisEstacoes.set(3, this.listaTotaisEstacoes.get(3) + total);
                    }

                    this.listaTotaisMes.set(j, total);
                    j++;
                    total = 0.0;
                } else {
                    //Esse cálculo fica aqui pois quando o j=11 ele não entra no if (acima).
                    if (j == 11) {
                        this.listaTotaisMes.set(j, total);
                        this.listaTotaisEstacoes.set(0, this.listaTotaisEstacoes.get(0) + total);
                    }
                    break;
                }
            } else {
                i++;
            }
        }

        setTextViewsTotais(listaTotaisMes, listaTotaisEstacoes);
    }

    /**
     * Método responsável por alterar o texto dos textviews dos totais dos meses e das estaçoes.
     */
    public void setTextViewsTotais(ArrayList<Double> listaTotaisMes, ArrayList<Double> listaTotaisEstacoes){
        if(listaTotaisMes.size() > 0){
            for(int j=0; j<listaTotaisMes.size(); j++){
                this.listaTextViewTotaisMes.get(j).setText(this.doisDecimais.format((listaTotaisMes.get(j))));
            }
        }

        TextView totalVer = findViewById(R.id.tv_AreaTotalVer);
        TextView totalOut = findViewById(R.id.tv_AreaTotalOut);
        TextView totalInv = findViewById(R.id.tv_AreaTotalInve);
        TextView totalPrim = findViewById(R.id.tv_AreaTotalPrim);

        if(listaTotaisEstacoes.size() > 0){
            totalVer.setText(this.doisDecimais.format(listaTotaisEstacoes.get(0)));
            totalOut.setText(this.doisDecimais.format(listaTotaisEstacoes.get(1)));
            totalInv.setText(this.doisDecimais.format(listaTotaisEstacoes.get(2)));
            totalPrim.setText(this.doisDecimais.format(listaTotaisEstacoes.get(3)));
        }
    }

    /**
     * Método chamado toda vez que o botão Próximo Passo é clicado, e tem como objetivo levar o usário para outra activity e mandar um array de totais/mês. (Obs: método é acionado, no onclick do Botao Proximo Passo, que se encontra em activity_piquete.xml)
     */
    public void irParaAnimaisActivity() {
        Intent i=new Intent(getApplicationContext(), AnimaisActivity.class);
        i.putExtra("nome_usuario", this.nomeUsuario);
        i.putExtra("areaTotal", this.areaTotal);
        startActivityForResult(i, this.CODIGO_REQUISICAO_ANIMAIS_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_REQUISICAO_ANIMAIS_ACTIVITY) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                nomeUsuario = data.getStringExtra("nome_usuario");
                ArrayList<Animais> listaAnimais = data.getParcelableArrayListExtra("listaAnimais");
                ArrayList<Double> listaTotalUAHA = (ArrayList<Double>) data.getSerializableExtra("listaTotaisUAHA");
                int qtdeAnimais = data.getIntExtra("qtdeAnimal", 0);
                double area = data.getDoubleExtra("area", 1.0);

                /*for(int i=0; i<listaAnimal.size(); i++){
                    Log.i("Animais", listaAnimal.get(i).getCategoria() + " " + listaAnimal.get(i).getConsumo() + " " + listaAnimal.get(i).getNumAnimais() + " " + listaAnimal.get(i).getEntradaMes() + " " + listaAnimal.get(i).getPesoInicial()+ " " + listaAnimal.get(i).getPesoFinal() + " " + listaAnimal.get(i).getPesoGanhoVer() + " " + listaAnimal.get(i).getPesoGanhoOut() + " " + listaAnimal.get(i).getPesoGanhoInv() + " " + listaAnimal.get(i).getPesoGanhoPrim() + " " + Arrays.toString(listaAnimal.get(i).getMeses()));
                }
                Log.i("Totais", String.valueOf(totais));*/

                Intent intent = new Intent();
                intent.putExtra("nome_usuario", nomeUsuario);
                intent.putExtra("listaAnimais", listaAnimais);
                intent.putExtra("listaTotaisUAHA", listaTotalUAHA);
                intent.putExtra("qtdeAnimal", qtdeAnimais);
                intent.putExtra("area", area);
                intent.putExtra("listaPiquetes", listaPiquetes);
                intent.putExtra("listaTotaisMes", listaTotaisMes);
                intent.putExtra("listaTotaisEstacoes", listaTotaisEstacoes);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
