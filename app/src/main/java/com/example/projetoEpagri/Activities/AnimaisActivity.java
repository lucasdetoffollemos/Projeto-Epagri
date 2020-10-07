package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnimaisActivity extends AppCompatActivity {
    private Button bt_adicionar_linha, bt_remover_linha, bt_finalizar_envio;
    private int posicaoLinhaTabela=-1, numeroDeLinhas=0;
    private TableLayout table_layout;
    private TableRow linha_tabela;
    private ArrayList<String> categoriaAnimal;
    private ArrayList<String> arrayMeses;
    private ArrayList<Double> qtdeAnimais; //array para armazenar os valores de qtde digitados para cada animal.
    private ArrayList<Animais> listaAnimais;
    ArrayList<Double> listaTotalUAHA;
    private ArrayList<double[]> matrizUA; //matrizes para mapear a UA de cada mês/linha.
    private double somaAnimal;
    //Consumo
    private double resultadoConsumo;
    //Número de animais

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);

        inicializa();
        setListeners();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        //Criando um array de String para as categorias de animais.
        categoriaAnimal = new ArrayList<>();
        categoriaAnimal.add("BEZERROS");
        categoriaAnimal.add("NOVILHOS JOVENS");
        categoriaAnimal.add("NOVILHOS ADULTOS");
        categoriaAnimal.add("BEZERRAS");
        categoriaAnimal.add("NOVILHAS JOVENS");
        categoriaAnimal.add("NOVILHAS ADULTAS");
        categoriaAnimal.add("VACAS (MATRIZES)");
        categoriaAnimal.add("TOUROS");

        //Criando um array de String para os meses.
        arrayMeses = new ArrayList<>();
        arrayMeses.add("Janeiro");
        arrayMeses.add("Fevereiro");
        arrayMeses.add("Março");
        arrayMeses.add("Abril");
        arrayMeses.add("Maio");
        arrayMeses.add("Junho");
        arrayMeses.add("Julho");
        arrayMeses.add("Agosto");
        arrayMeses.add("Setembro");
        arrayMeses.add("Outubro");
        arrayMeses.add("Novembro");
        arrayMeses.add("Dezembro");

        qtdeAnimais = new ArrayList<>();
        listaAnimais = new ArrayList<>();
        listaTotalUAHA = new ArrayList<>();
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);
        listaTotalUAHA.add(0.0);

        matrizUA = new ArrayList<>();

        table_layout = findViewById(R.id.table_layout);

        bt_adicionar_linha = findViewById(R.id.bt_adicionar_linha);
        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_finalizar_envio = findViewById(R.id.bt_finalizar_envio);
    }

    /**
     * Método utilizado para setar os listener dos botões.
     */
    public void setListeners(){
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Infla a linha para a tabela
                linha_tabela = (TableRow) View.inflate(AnimaisActivity.this, R.layout.tabela_demanda_atual_linha, null);
                adicionarLinhaTabela(linha_tabela);
                setListenersDinamicos(linha_tabela);

                posicaoLinhaTabela++;
                numeroDeLinhas++;

                if(qtdeAnimais.size() < numeroDeLinhas){
                    qtdeAnimais.add(0.0);
                }
            }
        });

        //ARRUMAR!
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(posicaoLinhaTabela));
                posicaoLinhaTabela--;

                if(posicaoLinhaTabela >= -1){
                    qtdeAnimais.remove(numeroDeLinhas-1);
                    matrizUA.remove(numeroDeLinhas-1);
                    listaAnimais.remove(numeroDeLinhas-1);

                    //Limpa a matriz com valores 0 para remover os cálculo dos totais.
                    //Se não eles continuam aparecendo mesmo após remover todas as linhas.
                    //Isso ocorre pois no método calculaTotais os valores dos textviews são setados em um while com condição
                    //matrizMeses.size() > 0.
                    double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
                    if(matrizUA.size() < numeroDeLinhas){
                        matrizUA.add(startArray);
                    }

                    numeroDeLinhas--;
                }
                else{
                    posicaoLinhaTabela = -1;
                }
                calculoUaHa(matrizUA);
            }
        });

        bt_finalizar_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaEnvio();
            }
        });
    }

    /**
     * Método responsável por adicionar uma linha na tabela demanda atual e configurar o adapter dos spinners.
     */
    private void adicionarLinhaTabela(TableRow linha_tabela){
        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        final Spinner spinnerCategoria = linha_tabela.findViewById(R.id.spinnerCategoria);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        final ArrayAdapter<String> spinnerCategoriaAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, categoriaAnimal);
        spinnerCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerCategoriaAdapter);

        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        Spinner spinnerMeses = linha_tabela.findViewById(R.id.spinnerMeses);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        ArrayAdapter<String> spinnerMesesAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, arrayMeses);
        spinnerMesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(spinnerMesesAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);
    }

    /**
     * Identifica os elementos da linha, dinamicamente, pelo seu index, e guarda os itens que foram selecionado, no spinner, ou armazenados no editText, para uso posterior
     * @param linha_tabela
     */
    public void setListenersDinamicos(final TableRow linha_tabela){
        final Spinner spinnerCategoria = (Spinner) linha_tabela.getChildAt(0);
        final TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
        final EditText etNumAnimais = (EditText) linha_tabela.getChildAt(2);
        final Spinner spinnerMeses = (Spinner) linha_tabela.getChildAt(3);
        final EditText etPesoInicial = (EditText) linha_tabela.getChildAt(4);
        final EditText etPesoFinal = (EditText) linha_tabela.getChildAt(5);
        final EditText etPesoVer = (EditText) linha_tabela.getChildAt(6);
        final EditText etPesoOut = (EditText) linha_tabela.getChildAt(7);
        final EditText etPesoInv = (EditText) linha_tabela.getChildAt(8);
        final EditText etPesoPrim = (EditText) linha_tabela.getChildAt(9);

        //Spinner categoria de animal.
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoria = spinnerCategoria.getSelectedItem().toString();

                resultadoConsumo = 0;
                //Define o consumo baseado na categoria de animal escolhido.
                switch (categoria){
                    case "BEZERROS":         { resultadoConsumo = 2;    break;}
                    case "NOVILHOS JOVENS":  { resultadoConsumo = 2.5;  break;}
                    case "NOVILHOS ADULTOS": { resultadoConsumo = 3;    break;}
                    case "BEZERRAS":         { resultadoConsumo = 2;    break;}
                    case "NOVILHAS JOVENS":  { resultadoConsumo = 2.5;  break;}
                    case "NOVILHAS ADULTOS": { resultadoConsumo = 3;    break;}
                    case "VACAS (MATRIZES)": { resultadoConsumo = 3;    break;}
                    case "TOUROS":           { resultadoConsumo = 2.5;  break;}
                    default:                 { resultadoConsumo = 2;    break;}
                }

                tv_consumo.setText(String.valueOf(resultadoConsumo));
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //EditText número de animais.
        etNumAnimais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Spinner meses.
        spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Edit Text Peso Inicial.
        etPesoInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text Peso Final.
        etPesoFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text Peso Verão.
        etPesoVer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit text Peso Outono.
        etPesoOut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit text Peso inverno.
        etPesoInv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text peso Primavera.
        etPesoPrim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lerValoresLinhaECalcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Método para converter um texto digitado num campo de texto para double.
     * @param et
     * @return
     */
    public double converteTextoEmDouble(EditText et){
        String texto = et.getText().toString();
        double textoEmDouble = 0.0;

        textoEmDouble = Double.parseDouble(texto);

        return textoEmDouble;
    }

    /**
     * Método para verificar se um Edit Text é vazio.
     * @param et
     * @return
     */
    public boolean verificaVazioET(EditText et){
        if(et.getText().toString().length() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Método para verificar se um Spinner é vazio.
     * @param p
     * @return
     */
    public boolean verificaVazioSP(Spinner p){
        if(p.getSelectedItem().toString().length() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Método responsável por ler os valores do spinner e das caixas de texto e realizar os cálculos.
     * @param linha_tabela
     * @param spinnerCategoria
     * @param etNumAnimais
     * @param spinnerMeses
     * @param etPesoInicial
     * @param etPesoFinal
     * @param etPesoVer
     * @param etPesoOut
     * @param etPesoInv
     * @param etPesoPrim
     */
    public void lerValoresLinhaECalcular(TableRow linha_tabela, Spinner spinnerCategoria, TextView textViewConsumo, EditText etNumAnimais, Spinner spinnerMeses, EditText etPesoInicial, EditText etPesoFinal, EditText etPesoVer, EditText etPesoOut, EditText etPesoInv, EditText etPesoPrim){
        String categoria;
        double consumo, numeroAnimais, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim;

        double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
        if(matrizUA.size() < numeroDeLinhas){
            matrizUA.add(startArray);
        }

        if(!verificaVazioSP(spinnerCategoria) &&
           !verificaVazioET(etNumAnimais) &&
           !verificaVazioSP(spinnerMeses) &&
           !verificaVazioET(etPesoInicial) &&
           !verificaVazioET(etPesoFinal) &&
           !verificaVazioET(etPesoVer) &&
           !verificaVazioET(etPesoOut) &&
           !verificaVazioET(etPesoInv) &&
           !verificaVazioET(etPesoPrim)){

            categoria = spinnerCategoria.getSelectedItem().toString();
            consumo = Double.parseDouble(textViewConsumo.getText().toString());
            numeroAnimais = converteTextoEmDouble(etNumAnimais);
            pesoInicial = converteTextoEmDouble(etPesoInicial);
            pesoFinal = converteTextoEmDouble(etPesoFinal);
            pesoVer = converteTextoEmDouble(etPesoVer);
            pesoOut = converteTextoEmDouble(etPesoOut);
            pesoInv = converteTextoEmDouble(etPesoInv);
            pesoPrim = converteTextoEmDouble(etPesoPrim);

            double peso_atual;
            double [] pesoVezesAnimal = new double[12];  double [] pesos = new double[12];

            String meses = spinnerMeses.getSelectedItem().toString();

            double ganho = 0.0;
            //Array que mapeia os pesos de acordo com as estações. Cada posição faz referência a um mês.
            double [] ganhoEstacao = new double[]{pesoVer, pesoVer, pesoOut, pesoOut, pesoOut, pesoInv, pesoInv, pesoInv, pesoPrim, pesoPrim, pesoPrim, pesoVer};
            int posicao = 0;  //posicão utilizada para inserir o animal em determinado mês de acordo com a entrada.
            int posicaoLinha = Integer.parseInt(linha_tabela.getTag().toString())+1;

            //Salva no array a quantidade de animais digitada para determinada linha.
            qtdeAnimais.set(posicaoLinha, numeroAnimais);

            //Define qual a posição que será utilizada no array ganhoEstacao e acordo com o mês de entrada do animal.
            switch (meses){
                case "Janeiro":   posicao = 0;  break;
                case "Fevereiro": posicao = 1;  break;
                case "Março":     posicao = 2;  break;
                case "Abril":     posicao = 3;  break;
                case "Maio":      posicao = 4;  break;
                case "Junho":     posicao = 5;  break;
                case "Julho":     posicao = 6;  break;
                case "Agosto":    posicao = 7;  break;
                case "Setembro":  posicao = 8;  break;
                case "Outubro":   posicao = 9;  break;
                case "Novembro":  posicao = 10; break;
                case "Dezembro":  posicao = 11; break;
            }

            //Estrutura de repetiçao feita para cada vez que o usuário trocar, o valor de entrada, os campos de textView de meses, limparem.
            for(int i = 10; i< 22; i++){
                TextView v = (TextView) linha_tabela.getChildAt(i);
                v.setText(String.valueOf(0));
            }

            int cont = 0;
            //Estrutura de repetiçao feita para gerar a sequência de somas que resulta no peso final estipulado pelo usuário.
            for(peso_atual = pesoInicial; peso_atual < pesoFinal;){
                ganho = (ganhoEstacao[posicao] * 30) / 1000;
                peso_atual = (peso_atual + ganho);

                //Mapeia para um array os pesos de cada mês já multiplicados pela quantidade de animais.
                //Esse array serve como entrada para a matriz.
                switch (posicao){
                    case 0: {
                        pesos[0] = peso_atual;
                        pesoVezesAnimal[0] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 1: {
                        pesos[1] = peso_atual;
                        pesoVezesAnimal[1] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 2: {
                        pesos[2] = peso_atual;
                        pesoVezesAnimal[2] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 3: {
                        pesos[3] = peso_atual;
                        pesoVezesAnimal[3] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 4: {
                        pesos[4] = peso_atual;
                        pesoVezesAnimal[4] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 5: {
                        pesos[5] = peso_atual;
                        pesoVezesAnimal[5] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 6: {
                        pesos[6] = peso_atual;
                        pesoVezesAnimal[6] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 7: {
                        pesos[7] = peso_atual;
                        pesoVezesAnimal[7] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 8: {
                        pesos[8] = peso_atual;
                        pesoVezesAnimal[8] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 9: {
                        pesos[9] = peso_atual;
                        pesoVezesAnimal[9] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 10: {
                        pesos[10] = peso_atual;
                        pesoVezesAnimal[10] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 11: {
                        pesos[11] = peso_atual;
                        pesoVezesAnimal[11] = peso_atual * numeroAnimais;
                        break;
                    }
                }

                TextView v = (TextView) linha_tabela.getChildAt(posicao + 10);
                v.setText(String.valueOf(peso_atual));
                posicao++;

                //Quando chega no mês de dezembro, dá a volta, vai para janeiro.
                if(posicao > 11){
                    posicao = 0;
                }

                //Flag para controlar que o número de meses que o animal permanece no campo não seja superior a 12.
                cont++;
                if(cont == 12){
                    //Toast.makeText(this, "Você extrapolou o limite de 12 meses do animal no campo. Altere os valores de ganhou ou peso final!", Toast.LENGTH_LONG).show();
                    break;
                }
            }

            matrizUA.set(posicaoLinha, pesoVezesAnimal);
            calculoUaHa(matrizUA);

            if(listaAnimais.size() < numeroDeLinhas){
                Animais temp = new Animais();
                listaAnimais.add(temp);
            }
            else{
                Animais animal = new Animais(categoria, consumo, numeroAnimais, meses, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim, pesos);
                listaAnimais.set(posicaoLinha, animal);
            }
        }
    }

    /**
     * Método responsável por percorrer a matriz e somar os valores coluna por coluna.
     * @param matriz
     * @return array com os valores totais por coluna.
     */
    public ArrayList<Double> percorreMatrizESoma(ArrayList<double[]> matriz){
        //i = linha | j = coluna.
        int i=0, j=0;
        double soma = 0.0;
        ArrayList<Double> resultado = new ArrayList<Double>();

        while (i<matriz.size()){
            soma = soma + matriz.get(i)[j];

            if((i+1) == matriz.size()) {
                i = 0;

                if(j+1 < 12){
                    resultado.add(soma);
                    j++;
                    soma = 0.0;
                }
                else{
                    if(j == 11){
                        resultado.add(soma);
                    }
                    break;
                }
            }
            else{
                i++;
            }
        }
        return resultado;
    }

    /**
     * Método responsável por realizar os cáculos de ua/ha e alterar o valor dos textviews.
     */
    public void calculoUaHa(ArrayList<double []> matrizUA){
        DecimalFormat doisDecimais = new DecimalFormat("#.##");

        //Calcula o total de animais.
        somaAnimal = 0.0;
        for(int i =0; i < qtdeAnimais.size(); i++){
            somaAnimal = somaAnimal + qtdeAnimais.get(i);
        }
        TextView quantidadeAnimal = findViewById(R.id.tv_totalAnimais);
        quantidadeAnimal.setText(String.valueOf(somaAnimal));

        //Transforma os totais de cada mês em UA (1 UA = 450KG.).
        ArrayList<Double> listaTotalUA = percorreMatrizESoma(matrizUA);
        for(int i=0; i<listaTotalUA.size(); i++){
            listaTotalUA.set(i, listaTotalUA.get(i) / 450);
        }

        //Recupera a área total vinda da Activity Piquete.
        double areaTotal = getIntent().getDoubleExtra("areaTotal", 1.0);

        for(int i=0; i<listaTotalUA.size(); i++){
            listaTotalUAHA.set(i, Double.parseDouble(doisDecimais.format(listaTotalUA.get(i) / areaTotal).replace(",",".")));
        }

        TextView totalJan = findViewById(R.id.tv_AreaUaMesJan);
        TextView totalFev = findViewById(R.id.tv_AreaUaMesFev);
        TextView totalMar = findViewById(R.id.tv_AreaUaMesMar);
        TextView totalAbr = findViewById(R.id.tv_AreaUaMesAbr);
        TextView totalMai = findViewById(R.id.tv_AreaUaMesMai);
        TextView totalJun = findViewById(R.id.tv_AreaUaMesJun);
        TextView totalJul = findViewById(R.id.tv_AreaUaMesJul);
        TextView totalAgo = findViewById(R.id.tv_AreaUaMesAgo);
        TextView totalSet = findViewById(R.id.tv_AreaUaMesSet);
        TextView totalOut = findViewById(R.id.tv_AreaUaMesOut);
        TextView totalNov = findViewById(R.id.tv_AreaUaMesNov);
        TextView totalDez = findViewById(R.id.tv_AreaUaMesDez);

        //Altera os valores dos TextViews.
        for(int i=0; i<listaTotalUAHA.size(); i++){
            switch (i){
                case 0:  { totalJan.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 1:  { totalFev.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 2:  { totalMar.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 3:  { totalAbr.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 4:  { totalMai.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 5:  { totalJun.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 6:  { totalJul.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 7:  { totalAgo.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 8:  { totalSet.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 9:  { totalOut.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 10: { totalNov.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
                case 11: { totalDez.setText(doisDecimais.format(listaTotalUAHA.get(i))); break; }
            }
        }

        //for(int k=0; k<listaTotalUAHA.size(); k++){
        //    Log.i("lista", "listaTotalUAHA[" + k + "] =" + String.valueOf(listaTotalUAHA.get(k)));
        //}
    }

    //ARRUMAR.
    public void finalizaEnvio() {
        Intent intent = new Intent();
        intent.putExtra("listaAnimais", listaAnimais);
        intent.putExtra("totais", listaTotalUAHA);
        setResult(RESULT_OK, intent);
        finish();
    }
}
