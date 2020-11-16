package com.example.projetoEpagri.Fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

//ARRUMAR A QUESTÃO DA SOMA ANIMAL. QUANDO CARREGA OS ANIMAIS NÃO ADICONA NA LISTA A QTDE.
public class FragmentDemandaAtual extends Fragment {
    private Button bt_adicionar_linha, bt_remover_linha, bt_atualizar;

    private TableLayout table_layout;
    private TableRow linha_tabela;
    private ArrayList<String> categoriaAnimal; //Array utilizado para setar os valores das categorias de animais no spinner categoria.
    private ArrayList<String> arrayMeses; //Array utilizado para setar os valores mostrados no spinner entrada
    private double resultadoConsumo; //Variável utilizada para setar o consumo de acordo com a categoria do animal escolhido.
    private View layout_incluido_animais; //Essa view serve para guardar a referência do layout (animais) incluído no layout do fragment.
    private TextView quantidadeAnimal;
    private int posicaoLinhaTabela=-1, numeroDeLinhas=0;

    private ArrayList<Double> qtdeAnimais; //Array para armazenar os valores de qtde digitados para cada animal.
    private ArrayList<double[]> matrizUA; //Matriz para mapear a UA de cada mês/linha.
    private ArrayList<Animais> listaAnimais;
    private ArrayList<Double> listaTotalUAHA;

    private int somaAnimal;
    private double areaTotal = 1.0;

    private String nomePropriedade;
    private int idPropriedade;
    private DecimalFormat doisDecimais;
    private boolean flagLoadAnimais = false;
    private Drawable drawable;

    //LinearLayout que será removido
    private LinearLayout linearLayoutToolBar;



    public FragmentDemandaAtual() {}

    public static FragmentDemandaAtual newInstance(String nomePropriedade) {
        FragmentDemandaAtual fragment = new FragmentDemandaAtual();

        Bundle args = new Bundle();
        args.putString("nomePropriedade", nomePropriedade);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_demanda_atual, container, false);

        layout_incluido_animais = (ConstraintLayout) rootView.findViewById(R.id.included_layout_animais);

        inicializa();
        setListeners();

        return rootView;
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        nomePropriedade = getArguments().getString("nomePropriedade");
        idPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(nomePropriedade);
        Propriedade p = MainActivity.bancoDeDados.propriedadeDAO.getPropriedade(nomePropriedade);
        areaTotal = p.getArea();

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
        matrizUA = new ArrayList<>();
        listaAnimais = MainActivity.bancoDeDados.animaisDAO.getAllAnimaisByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
        listaTotalUAHA = new ArrayList<>();
        for(int i=0; i<12; i++){
            listaTotalUAHA.add(0.0);
        }

        table_layout = layout_incluido_animais.findViewById(R.id.tableLayout_tabelaAnimais);
        quantidadeAnimal = layout_incluido_animais.findViewById(R.id.tv_totalAnimais);
        bt_adicionar_linha = layout_incluido_animais.findViewById(R.id.bt_adicionarLinha);
        bt_remover_linha = layout_incluido_animais.findViewById(R.id.bt_removerLinha);
        bt_atualizar = layout_incluido_animais.findViewById(R.id.bt_finalizarEnvio);
        bt_atualizar.setText("Atualizar Dados");
        drawable = bt_atualizar.getBackground();

        linearLayoutToolBar = layout_incluido_animais.findViewById(R.id.ln_toolBar_animais);
        linearLayoutToolBar.setVisibility(View.GONE);


        doisDecimais = new DecimalFormat("#.##");
        //Toast.makeText(AnimaisActivity.this, nomeUsuario, Toast.LENGTH_SHORT).show();

        for(int i=0; i<listaAnimais.size(); i++){
            adicionaLinha();
        }

    }

    /**
     * Método utilizado para setar os listener dos botões.
     */
    public void setListeners(){
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaLinha();
            }
        });

        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLinha();
                calculaTotalAnimais();
                calculoUaHa(matrizUA);
            }
        });

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDados();
            }
        });
    }

    /**
     * Método responsável por adicionar uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void adicionaLinha(){
        //Infla a linha para a tabela
        linha_tabela = (TableRow) View.inflate(getActivity(), R.layout.tabela_demanda_linha, null);
        criarLinha(linha_tabela);

        //Verifica se os animais já foram carregados. Se não foram, faz o loading.
        if(flagLoadAnimais == false){
            loadAnimais(linha_tabela);
            calculaTotalAnimais();

            if(numeroDeLinhas+1 == listaAnimais.size()){
                flagLoadAnimais = true; //Carregou todos os animais.
            }
        }

        setListenersLinha(linha_tabela);

        posicaoLinhaTabela++;
        numeroDeLinhas++;

        if(qtdeAnimais.size() < numeroDeLinhas){
            qtdeAnimais.add(0.0);
        }

        double [] startArray = {0,0,0,0,0,0,0,0,0,0,0,0};
        if(matrizUA.size() < numeroDeLinhas){
            matrizUA.add(startArray);
        }

        if(listaAnimais.size() < numeroDeLinhas){
            Animais temp = new Animais();
            listaAnimais.add(temp);
        }
    }

    /**
     * Método responsável por remover uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void removeLinha(){
        table_layout.removeView(table_layout.getChildAt(posicaoLinhaTabela));

        if(numeroDeLinhas > 0){
            qtdeAnimais.remove(numeroDeLinhas-1);
            matrizUA.remove(numeroDeLinhas-1);
            listaAnimais.remove(numeroDeLinhas-1);

            posicaoLinhaTabela--;
            numeroDeLinhas--;
        }
    }

    /**
     * Método responsável por adicionar uma linha na tabela demanda atual e configurar o adapter dos spinners.
     */
    private void criarLinha(TableRow linha_tabela){
        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        final Spinner spinnerCategoria = linha_tabela.findViewById(R.id.spinner_categoria);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        final ArrayAdapter<String> spinnerCategoriaAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categoriaAnimal);
        spinnerCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerCategoriaAdapter);

        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        Spinner spinnerMeses = linha_tabela.findViewById(R.id.spinner_meses);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        ArrayAdapter<String> spinnerMesesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayMeses);
        spinnerMesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(spinnerMesesAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);
    }

    /**
     * Método responsável por identificar a posição de um determinado valor dentro do spinner.
     * @param spinner
     * @param s
     * @return
     */
    private int getSpinnerIndex(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }

        return 0;
    }

    /**
     * Método responsável por carregar todos os animais do banco de dados.
     * @param linha_tabela representa a última linha adicionada.
     */
    public void loadAnimais(TableRow linha_tabela){
        final Spinner spinnerCategoria = (Spinner) linha_tabela.getChildAt(0);
        final EditText tv_consumo = (EditText) linha_tabela.getChildAt(1);
        final EditText etNumAnimais = (EditText) linha_tabela.getChildAt(2);
        final Spinner spinnerMeses = (Spinner) linha_tabela.getChildAt(3);
        final EditText etPesoInicial = (EditText) linha_tabela.getChildAt(4);
        final EditText etPesoFinal = (EditText) linha_tabela.getChildAt(5);
        final EditText etPesoVer = (EditText) linha_tabela.getChildAt(6);
        final EditText etPesoOut = (EditText) linha_tabela.getChildAt(7);
        final EditText etPesoInv = (EditText) linha_tabela.getChildAt(8);
        final EditText etPesoPrim = (EditText) linha_tabela.getChildAt(9);
        final TextView consumoJan = (TextView) linha_tabela.getChildAt(10);
        final TextView consumoFev = (TextView) linha_tabela.getChildAt(11);
        final TextView consumoMar = (TextView) linha_tabela.getChildAt(12);
        final TextView consumoAbr = (TextView) linha_tabela.getChildAt(13);
        final TextView consumoMai = (TextView) linha_tabela.getChildAt(14);
        final TextView consumoJun = (TextView) linha_tabela.getChildAt(15);
        final TextView consumoJul = (TextView) linha_tabela.getChildAt(16);
        final TextView consumoAgo = (TextView) linha_tabela.getChildAt(17);
        final TextView consumoSet = (TextView) linha_tabela.getChildAt(18);
        final TextView consumoOut = (TextView) linha_tabela.getChildAt(19);
        final TextView consumoNov = (TextView) linha_tabela.getChildAt(20);
        final TextView consumoDez = (TextView) linha_tabela.getChildAt(21);

        spinnerCategoria.setSelection(getSpinnerIndex(spinnerCategoria, listaAnimais.get(numeroDeLinhas).getCategoria()));
        tv_consumo.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getConsumo()));
        etNumAnimais.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getNumAnimais()));
        spinnerMeses.setSelection(getSpinnerIndex(spinnerMeses, listaAnimais.get(numeroDeLinhas).getEntradaMes()));
        etPesoInicial.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoInicial()));
        etPesoFinal.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoFinal()));
        etPesoVer.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoGanhoVer()));
        etPesoOut.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoGanhoOut()));
        etPesoInv.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoGanhoInv()));
        etPesoPrim.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getPesoGanhoPrim()));
        consumoJan.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(0)));
        consumoFev.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(1)));
        consumoMar.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(2)));
        consumoAbr.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(3)));
        consumoMai.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(4)));
        consumoJun.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(5)));
        consumoJul.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(6)));
        consumoAgo.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(7)));
        consumoSet.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(8)));
        consumoOut.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(9)));
        consumoNov.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(10)));
        consumoDez.setText(String.valueOf(listaAnimais.get(numeroDeLinhas).getMeses(11)));

        if(qtdeAnimais.size() <= numeroDeLinhas){
            qtdeAnimais.add(listaAnimais.get(numeroDeLinhas).getNumAnimais());
        }
    }

    /**
     * Identifica os elementos da linha, dinamicamente, pelo seu index, e guarda os itens que foram selecionado, no spinner, ou armazenados no editText, para uso posterior
     * @param linha_tabela
     */
    public void setListenersLinha(final TableRow linha_tabela){
        final Spinner spinnerCategoria = (Spinner) linha_tabela.getChildAt(0);
        final EditText tv_consumo = (EditText) linha_tabela.getChildAt(1);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                double numeroAnimais = 0.0;
                int posicao = Integer.parseInt(linha_tabela.getTag().toString())+1;

                if(!verificaVazioET(etNumAnimais)){
                    numeroAnimais = converteTextoEmDouble(etNumAnimais);
                }
                //Salva no array a quantidade de animais digitada para determinada linha.
                qtdeAnimais.set(posicao, numeroAnimais);

                calculaTotalAnimais();
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Spinner meses.
        spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
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
                calcular(linha_tabela, spinnerCategoria, tv_consumo, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Método responsável por calcular a soma total dos animais e atualizar o valor do TextView.
     */
    public void calculaTotalAnimais(){
        //Calcula o total de animais.
        somaAnimal = 0;

        if(!qtdeAnimais.isEmpty()){
            for(int i =0; i < qtdeAnimais.size(); i++){
                somaAnimal = (int) (somaAnimal + qtdeAnimais.get(i));
            }
        }

        quantidadeAnimal.setText(String.valueOf(somaAnimal));
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
    public void calcular(TableRow linha_tabela, Spinner spinnerCategoria, TextView textViewConsumo, EditText etNumAnimais, Spinner spinnerMeses, EditText etPesoInicial, EditText etPesoFinal, EditText etPesoVer, EditText etPesoOut, EditText etPesoInv, EditText etPesoPrim){
        String categoria;
        double consumo, numeroAnimais, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim;

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
            double [] pesoVezesQtdeAnimal = new double[12];
            double [] pesos = new double[12];

            String meses = spinnerMeses.getSelectedItem().toString();

            double ganho = 0.0;
            //Array que mapeia os pesos de acordo com as estações. Cada posição faz referência a um mês.
            double [] ganhoEstacao = new double[]{pesoVer, pesoVer, pesoOut, pesoOut, pesoOut, pesoInv, pesoInv, pesoInv, pesoPrim, pesoPrim, pesoPrim, pesoVer};
            int posicao = 0;  //posicão utilizada para inserir o animal em determinado mês de acordo com a entrada.
            int posicaoLinha = Integer.parseInt(linha_tabela.getTag().toString())+1;

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
                        pesoVezesQtdeAnimal[0] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 1: {
                        pesos[1] = peso_atual;
                        pesoVezesQtdeAnimal[1] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 2: {
                        pesos[2] = peso_atual;
                        pesoVezesQtdeAnimal[2] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 3: {
                        pesos[3] = peso_atual;
                        pesoVezesQtdeAnimal[3] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 4: {
                        pesos[4] = peso_atual;
                        pesoVezesQtdeAnimal[4] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 5: {
                        pesos[5] = peso_atual;
                        pesoVezesQtdeAnimal[5] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 6: {
                        pesos[6] = peso_atual;
                        pesoVezesQtdeAnimal[6] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 7: {
                        pesos[7] = peso_atual;
                        pesoVezesQtdeAnimal[7] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 8: {
                        pesos[8] = peso_atual;
                        pesoVezesQtdeAnimal[8] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 9: {
                        pesos[9] = peso_atual;
                        pesoVezesQtdeAnimal[9] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 10: {
                        pesos[10] = peso_atual;
                        pesoVezesQtdeAnimal[10] = peso_atual * numeroAnimais;
                        break;
                    }
                    case 11: {
                        pesos[11] = peso_atual;
                        pesoVezesQtdeAnimal[11] = peso_atual * numeroAnimais;
                        break;
                    }
                }

                TextView v = (TextView) linha_tabela.getChildAt(posicao + 10);
                v.setText(doisDecimais.format(peso_atual));
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

            matrizUA.set(posicaoLinha, pesoVezesQtdeAnimal);
            calculoUaHa(matrizUA);

            if(listaAnimais.size() >= numeroDeLinhas){
                Animais animal = new Animais(categoria, consumo, numeroAnimais, meses, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim, pesos);
                listaAnimais.set(posicaoLinha, animal);
            }
        }
        else{
            //Estrutura de repetiçao feita para cada vez que o usuário trocar, o valor de entrada, os campos de textView de meses, limparem.
            for(int i = 10; i< 22; i++){
                TextView v = (TextView) linha_tabela.getChildAt(i);
                v.setText(String.valueOf(0));
            }
        }
    }

    /**
     * Método responsável por realizar os cáculos de ua/ha e alterar o valor dos textviews.
     */
    public void calculoUaHa(ArrayList<double []> matrizUA){
        //Transforma os totais de cada mês em UA (1 UA = 450KG.).
        ArrayList<Double> listaTotalUA = percorreMatrizESoma(matrizUA);
        for(int i=0; i<listaTotalUA.size(); i++){
            listaTotalUA.set(i, listaTotalUA.get(i) / 450);
        }

        for(int i=0; i<listaTotalUA.size(); i++){
            listaTotalUAHA.set(i, Double.parseDouble(doisDecimais.format(listaTotalUA.get(i) / areaTotal).replace(",",".")));
        }

        TextView totalJan = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesJan);
        TextView totalFev = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesFev);
        TextView totalMar = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesMar);
        TextView totalAbr = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesAbr);
        TextView totalMai = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesMai);
        TextView totalJun = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesJun);
        TextView totalJul = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesJul);
        TextView totalAgo = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesAgo);
        TextView totalSet = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesSet);
        TextView totalOut = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesOut);
        TextView totalNov = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesNov);
        TextView totalDez = layout_incluido_animais.findViewById(R.id.tv_AreaUaMesDez);

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
        //Inicializa o array com os totais de cada mês com zero.
        for(int k=0; k<12; k++){
            resultado.add(k, 0.0);
        }

        while (i<matriz.size()){
            soma = soma + matriz.get(i)[j];

            if((i+1) == matriz.size()) {
                i = 0;

                if(j+1 < 12){
                    resultado.set(j, soma);
                    j++;
                    soma = 0.0;
                }
                else{
                    if(j == 11){
                        resultado.set(j, soma);
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

    public void atualizarDados() {
        //Deleta os valores antigos.
        MainActivity.bancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
        MainActivity.bancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

        //Salva os novos valores.
        for(int i=0; i<listaAnimais.size(); i++){
            MainActivity.bancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
        }

        MainActivity.bancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

        //Atualiza a qtde de animais na tabela de propriedades.
        MainActivity.bancoDeDados.propriedadeDAO.updatePropriedade(idPropriedade, somaAnimal);

        //bt_atualizar.setBackgroundColor(Color.GREEN);
        Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
    }
}