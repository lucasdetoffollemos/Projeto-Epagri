package com.example.projetoEpagri.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PiqueteFragment extends Fragment {
    public int posicaoLinhaTabela=-1, numeroDeLinhas=0;
    private TableLayout table_layout;

    private double producaoEstimadaD, areaTotal;
    private ArrayList<Double> listaDeAreas;             //lista com as áreas de todas as linhas.
    private ArrayList<double[]> matrizMeses;            //matriz que armazena os valores calculados de todas as linhas para todos os meses.
    private ArrayList<Double> listaTotaisMes;
    private ArrayList<Double> listaTotaisEstacoes;      //lista de 4 posiçõe para guardar os valores das estações.
    private ArrayList<Piquete> listaPiquetes;
    private ArrayList<TextView> listaTextViewTotaisMes; //lista com os textviews dos totais dos 12 meses.
    private Propriedade propriedade;
    private DecimalFormat doisDecimais;

    public PiqueteFragment() {}

    public static PiqueteFragment newInstance() {
        PiqueteFragment fragment = new PiqueteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(IndexFragment.propriedade != null){
            this.propriedade = IndexFragment.propriedade;
        }

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

        doisDecimais = new DecimalFormat("#.##");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.fragment_piquete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final Button bt_adicionar_linha = getView().findViewById(R.id.bt_adicionarLinha);
        final Button bt_remover_linha = getView().findViewById(R.id.bt_removerLinha);
        final Button bt_proximo_passo = getView().findViewById(R.id.bt_finalizarEnvio);

        final TextView jan = getView().findViewById(R.id.tv_AreaTotalMesJan);
        final TextView fev = getView().findViewById(R.id.tv_AreaTotalMesFev);
        final TextView mar = getView().findViewById(R.id.tv_AreaTotalMesMar);
        final TextView abr = getView().findViewById(R.id.tv_AreaTotalMesAbr);
        final TextView mai = getView().findViewById(R.id.tv_AreaTotalMesMai);
        final TextView jun = getView().findViewById(R.id.tv_AreaTotalMesJun);
        final TextView jul = getView().findViewById(R.id.tv_AreaTotalMesJul);
        final TextView ago = getView().findViewById(R.id.tv_AreaTotalMesAgo);
        final TextView set = getView().findViewById(R.id.tv_AreaTotalMesSet);
        final TextView out = getView().findViewById(R.id.tv_AreaTotalMesOut);
        final TextView nov = getView().findViewById(R.id.tv_AreaTotalMesNov);
        final TextView dez = getView().findViewById(R.id.tv_AreaTotalMesDez);

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

        table_layout = getView().findViewById(R.id.tableLayout_tabelaAnimais);

        adicionaLinha();

        //Clique no ícone "voltar".
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getFragmentManager().popBackStack();
            }
        });

        //Clique no botão "+".
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaLinha();
            }
        });

        //Clique no botão "-".
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numeroDeLinhas > 1){
                    removeLinha();
                    calculaTotais();
                }
                else{
                    Toast.makeText(getActivity(), "Operação Inválida!! Você deve manter pelo menos 1 linha na tabela!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Clique no botão "Próximo passo".
        bt_proximo_passo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listaDeAreas.contains(0.0)){
                    IndexFragment.propriedade.setArea(areaTotal);
                    IndexFragment.propriedade.setListaPiqueteAtual(listaPiquetes);
                    IndexFragment.listaTotaisMes = new ArrayList<>(listaTotaisMes);
                    IndexFragment.listaTotaisEstacoes = new ArrayList<>(listaTotaisEstacoes);

                    Fragment animais_fragment = AnimaisFragment.newInstance();
                    MainActivity.startFragment(animais_fragment, "animais_fragment", R.id.ll_index, true, true, getActivity());
                }
                else{
                    Toast.makeText(getActivity(), "Você deve preencher todos os campos \"Área\" antes de prosseguir!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método responsável por adicionar uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void adicionaLinha(){
        //Infla a linha para a tabela
        TableRow linha_tabela = (TableRow) View.inflate(getActivity(), R.layout.tabela_oferta_linha, null);
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

    /**
     * Método responsável por remover uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void removeLinha(){
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
    }

    /**
     * Método responsável por adicionar uma linha na tabela oferta atual e configurar o adapter dos spinners.
     */
    private void criarLinha(TableRow linha_tabela){
        // Array que armazena os tipos de piquetes, vindos do arquivo DadosDAO.java.
        ArrayList<String> tipoPiquete = new ArrayList<>();

        if(this.propriedade.getRegiao().equals("cfa")){
            tipoPiquete = BancoDeDados.dadosDAO.getTiposPastagem(IDadosSchema.TABELA_DADOS_NORTE);
        }
        else if(this.propriedade.getRegiao().equals("cfb")){
            tipoPiquete = BancoDeDados.dadosDAO.getTiposPastagem(IDadosSchema.TABELA_DADOS_SUL);
        }

        //Localiza o spinner tipo no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinner_tipoPiquete);
        //Cria um ArrayAdpter usando o array de string com os tipos armazenados no banco de dados.
        ArrayAdapter<String> spinnerTipoAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tipoPiquete);
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
        ArrayAdapter<String> spinnerCondicaoAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, condicaoPiquete);
        spinnerCondicaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicaoPiquete.setAdapter(spinnerCondicaoAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);

        //Adicionando as Linhas na tabela.
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
     * @param linha_tabela Representa a linha adicionada na tabela.
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
     * @param linha_tabela Representa a linha preenchida com os dados.
     * @param spinnerTipo Representa o spinner com o tipo da pastagem.
     * @param spinnerCondicao Representa o spinner com a condição da pastagem.
     * @param editTextArea Representa o campo de texto com a área da pastagem.
     */
    public void calcular(final TableRow linha_tabela, Spinner spinnerTipo, Spinner spinnerCondicao, EditText editTextArea){
        //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
        String tipo = spinnerTipo.getSelectedItem().toString();
        String condicao = spinnerCondicao.getSelectedItem().toString();
        String areaS = editTextArea.getText().toString();

        int posicao = Integer.parseInt(linha_tabela.getTag().toString())+1;

        //Adiciona as áreas digitadas na lista de áreas.
        double areaD;
        if(areaS.length() > 0) {
            areaD = Double.parseDouble(areaS);
        }
        else{
            areaD = 0;
        }
        this.listaDeAreas.set(posicao, areaD);

        //Cálcula a producão estimada.
        calculaProducaoEstimada(linha_tabela, tipo, condicao);

        //Cálcula a produção para cada mês. E faz a soma de todos os meses
        double [] arrayMesesProd = new double[12];
        double totalToneladaAnual = 0;
        for(int i=1; i<=12; i++){
            arrayMesesProd[i-1] = calculaMes(linha_tabela, tipo, condicao, areaD, i);
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
        int intTotalTonelada = (int) totalToneladaAnual;
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
    public void calculaProducaoEstimada(final TableRow linha_tabela, String tipoPastagem, String condicao){
        TextView tv_prod = (TextView) linha_tabela.getChildAt(3); //posição da coluna produção estimada.

        if(this.propriedade.getRegiao().equals("cfa")){
            this.producaoEstimadaD = (BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_NORTE)) * 1000; //kg
        }else if(this.propriedade.getRegiao().equals("cfb")){
            this.producaoEstimadaD = (BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_SUL)) * 1000;
        }

        String producaoEstimada = this.doisDecimais.format(this.producaoEstimadaD);
        tv_prod.setText(producaoEstimada);
    }

    /**
     * Método para calcular a produção em cada um dos meses.
     * @param linha_tabela Representa a linha da tabela da qual os valores serão calculados.
     * @param tipoPastagem Representa o tipo da pastagem.
     * @param condicao Representa a condição da pastagem.
     * @param area Representa a área da pastagem.
     * @param mes Representa o valor númerico do mês (1 a 12).
     */
    public double calculaMes(final TableRow linha_tabela, String tipoPastagem, String condicao, double area, int mes){
        Double aproveitamento = 0.60;

        //Janeiro está na posição 4, por isso mes+3.
        TextView tv_mes = (TextView) linha_tabela.getChildAt(mes+3);

        double valor = 0.0;
        if(this.propriedade.getRegiao().equals("cfa")){
            valor = (float) BancoDeDados.dadosDAO.getMeses(mes, tipoPastagem, IDadosSchema.TABELA_DADOS_NORTE)/100;
            valor = Double.parseDouble(this.doisDecimais.format((BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_NORTE)) * aproveitamento * valor * area).replace(",", "."));
        }
        else if(this.propriedade.getRegiao().equals("cfb")){
            valor = (float) BancoDeDados.dadosDAO.getMeses(mes, tipoPastagem, IDadosSchema.TABELA_DADOS_SUL)/100;
            valor = Double.parseDouble(this.doisDecimais.format((BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_SUL)) * aproveitamento * valor * area).replace(",", "."));
        }

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
        TextView area = getActivity().findViewById(R.id.tv_AreaTotalNumHa);
        area.setText(this.doisDecimais.format(this.areaTotal));

        double total = 0.0;
        int i = 0, j = 0;

        //Limpa os valores dos totais da estação antes de calcular.
        this.listaTotaisEstacoes.set(0, 0.0);
        this.listaTotaisEstacoes.set(1, 0.0);
        this.listaTotaisEstacoes.set(2, 0.0);
        this.listaTotaisEstacoes.set(3, 0.0);

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
                        this.listaTotaisEstacoes.set(0, Double.parseDouble(doisDecimais.format(this.listaTotaisEstacoes.get(0) + total).replace(",", ".")));
                    }

                    //Outono
                    if (j == 2 || j == 3 || j == 4) {
                        this.listaTotaisEstacoes.set(1, Double.parseDouble(doisDecimais.format(this.listaTotaisEstacoes.get(1) + total).replace(",", ".")));
                    }

                    //Inverno
                    if (j == 5 || j == 6 || j == 7) {
                        this.listaTotaisEstacoes.set(2, Double.parseDouble(doisDecimais.format(this.listaTotaisEstacoes.get(2) + total).replace(",", ".")));
                    }

                    //Primavera
                    if (j == 8 || j == 9 || j == 10) {
                        this.listaTotaisEstacoes.set(3, Double.parseDouble(doisDecimais.format(this.listaTotaisEstacoes.get(3) + total).replace(",", ".")));
                    }

                    this.listaTotaisMes.set(j, Double.parseDouble(doisDecimais.format(total).replace(",", ".")));
                    j++;
                    total = 0.0;
                } else {
                    //Esse cálculo fica aqui pois quando o j=11 ele não entra no if (acima).
                    if (j == 11) {
                        this.listaTotaisMes.set(j, Double.parseDouble(doisDecimais.format(total).replace(",", ".")));
                        this.listaTotaisEstacoes.set(0, Double.parseDouble(doisDecimais.format(this.listaTotaisEstacoes.get(0) + total).replace(",", ".")));
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

        TextView totalVer = getView().findViewById(R.id.tv_AreaTotalVer);
        TextView totalOut = getView().findViewById(R.id.tv_AreaTotalOut);
        TextView totalInv = getView().findViewById(R.id.tv_AreaTotalInve);
        TextView totalPrim = getView().findViewById(R.id.tv_AreaTotalPrim);

        if(listaTotaisEstacoes.size() > 0){
            totalVer.setText(this.doisDecimais.format(listaTotaisEstacoes.get(0)));
            totalOut.setText(this.doisDecimais.format(listaTotaisEstacoes.get(1)));
            totalInv.setText(this.doisDecimais.format(listaTotaisEstacoes.get(2)));
            totalPrim.setText(this.doisDecimais.format(listaTotaisEstacoes.get(3)));
        }
    }
}