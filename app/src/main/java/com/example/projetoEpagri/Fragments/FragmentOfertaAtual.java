package com.example.projetoEpagri.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FragmentOfertaAtual extends Fragment {
    private Button bt_adicionar_linha, bt_remover_linha, bt_atualizar;
    private TableLayout table_layout;
    private View layout_incluido_piquete; //Essa view serve para guardar a referência do layout (piquete) incluído no layout do fragment.
    public int posicaoLinhaTabela=-1, numeroDeLinhas=0;

    private double producaoEstimadaD, areaTotal;
    private ArrayList<Double> listaDeAreas;             //lista com as áreas de todas as linhas.
    private ArrayList<double[]> matrizMeses;            //matriz que armazena os valores calculados de todas as linhas para todos os meses.
    private ArrayList<Double> listaTotaisMes;
    private ArrayList<Double> listaTotaisEstacoes;      //lista de 4 posiçõe para guardar os valores das estações.
    private ArrayList<Piquete> listaPiquetes;
    private ArrayList<TextView> listaTextViewTotaisMes; //lista com os textviews dos totais dos 12 meses.

    private String regiao;
    private int idPropriedade;
    private DecimalFormat doisDecimais;

    private boolean flagLoadPiquete = false;  //Flag para setar os valores dos piquetes já existentes no momento de criação das linhas.
    private Drawable drawable;

    public FragmentOfertaAtual() {}

    public static FragmentOfertaAtual newInstance(String nomePropriedade) {
        FragmentOfertaAtual fragment = new FragmentOfertaAtual();

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
        View rootView = inflater.inflate(R.layout.fragment_oferta_atual, container, false);

        layout_incluido_piquete = rootView.findViewById(R.id.included_layout_piquete);

        inicializa();
        setListeners();

        return rootView;
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        String nomePropriedade = "";
        if(getArguments() != null){
            nomePropriedade = getArguments().getString("nomePropriedade");
        }

        idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(nomePropriedade);
        regiao = BancoDeDados.propriedadeDAO.getPropriedadeRegiao(nomePropriedade);

        listaDeAreas = new ArrayList<>();
        matrizMeses = new ArrayList<>();
        listaTotaisMes = new ArrayList<>();
        listaTotaisEstacoes = new ArrayList<>();
        listaPiquetes = BancoDeDados.piqueteDAO.getAllPiquetesByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
        listaTextViewTotaisMes = new ArrayList<>();

        //Toast.makeText(getActivity(), "executei", Toast.LENGTH_SHORT).show();

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

        TextView jan = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesJan);
        TextView fev = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesFev);
        TextView mar = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesMar);
        TextView abr = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesAbr);
        TextView mai = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesMai);
        TextView jun = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesJun);
        TextView jul = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesJul);
        TextView ago = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesAgo);
        TextView set = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesSet);
        TextView out = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesOut);
        TextView nov = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesNov);
        TextView dez = layout_incluido_piquete.findViewById(R.id.tv_AreaTotalMesDez);

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

        table_layout = layout_incluido_piquete.findViewById(R.id.tableLayout_tabelaAnimais);
        bt_adicionar_linha = layout_incluido_piquete.findViewById(R.id.bt_adicionarLinha);
        bt_remover_linha = layout_incluido_piquete.findViewById(R.id.bt_removerLinha);
        bt_atualizar = layout_incluido_piquete.findViewById(R.id.bt_finalizarEnvio);
        bt_atualizar.setText(R.string.txt_bt_atualizar);
        drawable = bt_atualizar.getBackground();
        //LinearLayout que será removido
        LinearLayout linearLayoutToolBar = layout_incluido_piquete.findViewById(R.id.ln_toolBar_piquete);
        linearLayoutToolBar.setVisibility(View.GONE);

        doisDecimais = new DecimalFormat("#.##");

        //adicionaLinha();

        //LER A LISTA DE PIQUETES E ADICIONAR AS LINHAS DE ACORDO.
        for(int i=0; i<listaPiquetes.size(); i++){
            //Infla a linha para a tabela
            adicionaLinha();
        }
    }

    /**
     * Método utilizado para setar os listener dos botões.
     */
    public void setListeners(){
        //Quando clicado no botao de mais, é acionado está funçao.
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaLinha();
            }
        });

        //Quando clicado no botao de menos, é acionado está funçao, que tem como objetivo excluir cada linha da tabela.
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

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listaDeAreas.contains(0.0)){
                    atualizarDados(listaPiquetes, idPropriedade);
                }
                else{
                    Toast.makeText(getActivity(), "Você deve preencher todos os campos \"Área\" antes atualizar os dados!", Toast.LENGTH_SHORT).show();
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

        //Verifica se os piquetes já foram carregados. Se não foram, faz o loading.
        if(!flagLoadPiquete){
            loadPiquetes(linha_tabela);

            if(numeroDeLinhas+1 == listaPiquetes.size()){
                flagLoadPiquete = true; //Carregou todos os piquetes.
            }
        }
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

        if(regiao.equals("cfa")){
            tipoPiquete = BancoDeDados.dadosDAO.getTiposPastagem(IDadosSchema.TABELA_DADOS_NORTE);
        }
        else if(regiao.equals("cfb")){
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


    /**
     * Método responsável por identificar a posição de um determinado valor dentro do spinner.
     * @param spinner Representa o spinner que contém os valores.
     * @param s Representa o valor que deseja-se encontrar.
     * @return Retorna a posição do valor dentro da lista de valores do spinner.
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
     * Método responsável por carregar todos os piquetes do banco de dados.
     * @param linha_tabela representa a última linha adicionada.
     */
    public void loadPiquetes(TableRow linha_tabela){
        final Spinner spinnerTipo = (Spinner) linha_tabela.getChildAt(0);
        final Spinner spinnerCondicao = (Spinner) linha_tabela.getChildAt(1);
        final EditText editTextArea = (EditText) linha_tabela.getChildAt(2);
        final TextView prodEstimada = (TextView) linha_tabela.getChildAt(3);
        final TextView prodJan = (TextView) linha_tabela.getChildAt(4);
        final TextView prodFev = (TextView) linha_tabela.getChildAt(5);
        final TextView prodMar = (TextView) linha_tabela.getChildAt(6);
        final TextView prodAbr = (TextView) linha_tabela.getChildAt(7);
        final TextView prodMai = (TextView) linha_tabela.getChildAt(8);
        final TextView prodJun = (TextView) linha_tabela.getChildAt(9);
        final TextView prodJul = (TextView) linha_tabela.getChildAt(10);
        final TextView prodAgo = (TextView) linha_tabela.getChildAt(11);
        final TextView prodSet = (TextView) linha_tabela.getChildAt(12);
        final TextView prodOut = (TextView) linha_tabela.getChildAt(13);
        final TextView prodNov = (TextView) linha_tabela.getChildAt(14);
        final TextView prodDez = (TextView) linha_tabela.getChildAt(15);
        final TextView total = (TextView) linha_tabela.getChildAt(16);

        spinnerTipo.setSelection(getSpinnerIndex(spinnerTipo, listaPiquetes.get(numeroDeLinhas).getTipo()));
        spinnerCondicao.setSelection(getSpinnerIndex(spinnerCondicao, listaPiquetes.get(numeroDeLinhas).getCondicao()));
        editTextArea.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getArea()));
        prodEstimada.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getProdEstimada()));
        prodJan.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(0)));
        prodFev.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(1)));
        prodMar.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(2)));
        prodAbr.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(3)));
        prodMai.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(4)));
        prodJun.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(5)));
        prodJul.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(6)));
        prodAgo.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(7)));
        prodSet.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(8)));
        prodOut.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(9)));
        prodNov.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(10)));
        prodDez.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getMeses(11)));
        total.setText(String.valueOf(listaPiquetes.get(numeroDeLinhas).getTotal()));
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
     * @param linha_tabela Representa a linha que foi adicionada na tabela.
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

        bt_atualizar.setBackground(drawable);

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
        calculaProducaoEstimada(linha_tabela, tipo, condicao, areaD);

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
    public void calculaProducaoEstimada(final TableRow linha_tabela, String tipoPastagem, String condicao, double area){
        TextView tv_prod = (TextView) linha_tabela.getChildAt(3); //posição da coluna produção estimada.

        if(regiao.equals("cfa")){
            this.producaoEstimadaD = (BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_NORTE)) * area;
        }else if(regiao.equals("cfb")){
            this.producaoEstimadaD = (BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_SUL)) * area;
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
        if(regiao.equals("cfa")){
            valor = (float) BancoDeDados.dadosDAO.getMeses(mes, tipoPastagem, IDadosSchema.TABELA_DADOS_NORTE)/100;
            valor = Double.parseDouble(this.doisDecimais.format((BancoDeDados.dadosDAO.getCondicao(tipoPastagem, condicao, IDadosSchema.TABELA_DADOS_NORTE)) * aproveitamento * valor * area).replace(",", "."));
        }
        else if(regiao.equals("cfb")){
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

        TextView area;
        if(getView() != null){
            area = getView().findViewById(R.id.tv_AreaTotalNumHa);
            area.setText(this.doisDecimais.format(this.areaTotal));
        }

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
    @SuppressWarnings("ConstantConditions")
    public void setTextViewsTotais(ArrayList<Double> listaTotaisMes, ArrayList<Double> listaTotaisEstacoes){
        if(listaTotaisMes.size() > 0){
            for(int j=0; j<listaTotaisMes.size(); j++){
                this.listaTextViewTotaisMes.get(j).setText(this.doisDecimais.format((listaTotaisMes.get(j))));
            }
        }

        TextView totalVer = null, totalOut = null, totalInv = null, totalPrim = null;

        if(getView() != null){
            totalVer = getView().findViewById(R.id.tv_AreaTotalVer);
            totalOut = getView().findViewById(R.id.tv_AreaTotalOut);
            totalInv = getView().findViewById(R.id.tv_AreaTotalInve);
            totalPrim = getView().findViewById(R.id.tv_AreaTotalPrim);
        }

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
    public void atualizarDados(ArrayList<Piquete> listaPiquetes, int idPropriedade) {
        //Deleta os valores antigos.
        BancoDeDados.piqueteDAO.deletePiqueteByPropId(idPropriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
        BancoDeDados.totalPiqueteMesDAO.deleteTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
        BancoDeDados.totalPiqueteEstacaoDAO.deleteTotalEstacaoByPropId(idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);

        //Salva os novos valores.
        for(int i=0; i<listaPiquetes.size(); i++){
            BancoDeDados.piqueteDAO.inserirPiquete(listaPiquetes.get(i), idPropriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
        }

        BancoDeDados.totalPiqueteMesDAO.inserirTotalMes(listaTotaisMes, idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
        BancoDeDados.totalPiqueteEstacaoDAO.inserirTotalEstacao(listaTotaisEstacoes, idPropriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);

        //Atualiza a área total na tabela de propriedades.
        BancoDeDados.propriedadeDAO.updatePropriedade(idPropriedade, areaTotal);

        //bt_atualizar.setBackgroundColor(Color.GREEN);
        Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
    }
}