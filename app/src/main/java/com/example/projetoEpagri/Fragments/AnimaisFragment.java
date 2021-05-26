package com.example.projetoEpagri.Fragments;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.projetoEpagri.BancoDeDadosSchema.IAnimaisSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.IPiqueteSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnimaisFragment extends Fragment {
    private static final String ARG_PARAM1 = "id_propriedade";
    private static final String ARG_PARAM2 = "load";
    private static final String ARG_PARAM3 = "atual_ou_proposta";

    private boolean load, load_complete = true, tabela_vazia = true;
    private int id_propriedade;
    private String modo;

    private ArrayList<String> categoriaAnimal; //Array utilizado para setar os valores das categorias de animais no spinner categoria.
    private ArrayList<String> arrayMeses; //Array utilizado para setar os valores mostrados no spinner entrada

    private ArrayList<Double> qtdeAnimais; //Array para armazenar os valores de qtde digitados para cada animal.
    private ArrayList<double[]> matrizUA; //Matriz para mapear a UA de cada mês/linha.
    private ArrayList<Animais> listaAnimais;
    private ArrayList<Double> listaTotalUAHA;

    private double resultadoConsumo; //Variável utilizada para setar o consumo de acordo com a categoria do animal escolhido.
    private int posicaoLinhaTabela = -1, numeroDeLinhas = 0;

    private int somaAnimal;
    private DecimalFormat doisDecimais;

    private TableLayout table_layout, saved_table_layout;
    private TableRow linha_tutorial;

    public AnimaisFragment() {
    }

    public static AnimaisFragment newInstance(int id, boolean load, String modo) {
        AnimaisFragment fragment = new AnimaisFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putBoolean(ARG_PARAM2, load);
        args.putString(ARG_PARAM3, modo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            id_propriedade = getArguments().getInt(ARG_PARAM1);
            load = getArguments().getBoolean(ARG_PARAM2);
            modo = getArguments().getString(ARG_PARAM3);
        }

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
        arrayMeses.add("Jan");
        arrayMeses.add("Fev");
        arrayMeses.add("Mar");
        arrayMeses.add("Abr");
        arrayMeses.add("Mai");
        arrayMeses.add("Jun");
        arrayMeses.add("Jul");
        arrayMeses.add("Ago");
        arrayMeses.add("Set");
        arrayMeses.add("Out");
        arrayMeses.add("Nov");
        arrayMeses.add("Dez");

        qtdeAnimais = new ArrayList<>();
        matrizUA = new ArrayList<>();
        listaAnimais = new ArrayList<>();
        listaTotalUAHA = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            listaTotalUAHA.add(0.0);
        }

        doisDecimais = new DecimalFormat("#.##");

        if (modo != null) {
            if (modo.equals("atual")) {
                listaAnimais = BancoDeDados.animaisDAO.getAllAnimaisByPropId(id_propriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
            } else if (modo.equals("proposta")) {
                listaAnimais = BancoDeDados.animaisDAO.getAllAnimaisByPropId(id_propriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);
            }

            //Se a lista de piquetes retornados do banco for maior que 0, significa que precisa carregar esses piquetes para a tabela.
            //Significa ainda que a tabela que será mostrada não será vazia, por isso muda-se .
            if (listaAnimais.size() > 0) {
                load_complete = false;
                tabela_vazia = false;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.fragment_animais, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View toolbar = getView().findViewById(R.id.included_toolbar);
        final View tabela_animais = getView().findViewById(R.id.included_tabela_animais);
        final View bottom_bar = getView().findViewById(R.id.included_bottom_bar);
        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final ImageView iv_vaca = getView().findViewById(R.id.iv_vaca);
        Button bt_adicionar_linha = getView().findViewById(R.id.bt_adicionarLinha);
        Button bt_remover_linha = getView().findViewById(R.id.bt_removerLinha);
        Button bt_finalizar_atualizar = getView().findViewById(R.id.bt_proximo);
        final TextView toolbar_title = toolbar.findViewById(R.id.tv_titulo_toolbar);
        toolbar_title.setText(R.string.tb_title_cadastrar_animais);

        table_layout = tabela_animais.findViewById(R.id.tableLayout_tabelaAnimais);

        bt_finalizar_atualizar.setText(R.string.txt_bt_finalizar);

        //Se o fragment for criado com a opção "carregar"significa que ele está sendo aberto dentro do VerDadosFragment.
        //Sendo assim, desabilita-se o toolbar.
        if (load) {
            toolbar.setVisibility(View.GONE);
            bottom_bar.setVisibility(View.GONE);

            Fragment parent = getParentFragment();
            View v = parent.getView().findViewById(R.id.included_bottom_bar_ver_dados);

            bt_adicionar_linha = v.findViewById(R.id.bt_adicionarLinha);
            bt_remover_linha = v.findViewById(R.id.bt_removerLinha);
            bt_finalizar_atualizar = v.findViewById(R.id.bt_proximo);

            bt_finalizar_atualizar.setText(R.string.txt_bt_atualizar_dados);

            //Se estiver no modo de load, insere-se linhas na tabela de acordo com o número de piquetes cadastrados no banco de dados.
            if (listaAnimais.size() > 0 && !load_complete) {
                load_complete = false;

                for (int i = 0; i < listaAnimais.size(); i++) {
                    adicionaLinha();
                }
            } else { //Caso de estar no modo proposta sem a proposta ter sido feita.
                if (tabela_vazia) {
                    adicionaLinha();
                    tabela_vazia = false;
                }

            }
        } else { //caso contrário adiciona-se uma única linha na tabela.
            if (saved_table_layout == null) {
                adicionaLinha();
            }
        }

        //Clique no botão voltar. Desfaz as operações realizadas no PiqueteFragment.
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!load) {
                    IndexFragment.propriedade.setArea(0.0);
                    IndexFragment.propriedade.setListaPiqueteAtual(null);
                    IndexFragment.listaTotaisMes = null;
                    IndexFragment.listaTotaisEstacoes = null;
                }

                getFragmentManager().popBackStack();
            }
        });

        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaLinha();
            }
        });

        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeroDeLinhas > 1) {
                    removeLinha();
                    calculaTotalAnimais();
                    calculoUaHa(matrizUA);
                } else {
                    Toast.makeText(getActivity(), "Operação Inválida! Você deve manter pelo menos 1 linha na tabela!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_finalizar_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAnimais(listaAnimais)) {
                    if (load) {
                        atualizarDados(listaAnimais, id_propriedade);
                    } else {
                        finalizar();
                    }
                } else {
                    Toast.makeText(getActivity(), "Você deve preencher todos os campos antes de finalizar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        IndexFragment.tutorial = MainActivity.bancoDeDados.tutorialDAO.getTutorial();

        if (IndexFragment.tutorial != null && IndexFragment.tutorial.getInserir_animais() == 0) {
            iv_vaca.setVisibility(View.VISIBLE);

            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(iv_vaca, "Etapa 3", "Nessa etapa você deve cadastrar os animais existentes na propriedade.\n\nCada linha da tabela representa um grupo diferente de animais.")
                                    .id(1)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView((Spinner) linha_tutorial.getChildAt(0), "Categoria", "Escolha a categoria dos animais.")
                                    .id(2)
                                    .titleTextSize(20)
                                    .targetRadius(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .transparentTarget(true),
                            TapTarget.forView((EditText) linha_tutorial.getChildAt(1), "Quantidade", "Escreva no campo de texto a quantidade de animais.")
                                    .id(3)
                                    .titleTextSize(20)
                                    .targetRadius(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .transparentTarget(true),
                            TapTarget.forView((Spinner) linha_tutorial.getChildAt(2), "Entrada no Pasto", "Escolha o mês que os animais começaram a pastar.")
                                    .id(4)
                                    .titleTextSize(20)
                                    .targetRadius(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .transparentTarget(true),
                            TapTarget.forView((EditText) linha_tutorial.getChildAt(3), "Peso Inicial", "Escreva no campo de texto qual a média de peso inicial dos animais.")
                                    .id(5)
                                    .titleTextSize(20)
                                    .targetRadius(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false)
                                    .transparentTarget(true),
                            TapTarget.forView((EditText) linha_tutorial.getChildAt(4), "Peso Final", "Escreva no campo de texto qual a média de peso final dos animais.")
                                    .id(6)
                                    .titleTextSize(20)
                                    .targetRadius(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false)
                                    .transparentTarget(true),
                            TapTarget.forView(iv_vaca, "Ganho de Peso por Estação", "Você ainda pode editar qual o ganho de peso (em gramas) por dia dos diferentes grupos de animais de acordo com a estação do ano.")
                                    .id(7)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(bt_adicionar_linha, "Adicionar Animais", "Você pode adicionar mais animais clicando no botão \"+\".")
                                    .id(8)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(bt_remover_linha, "Remover Animais", "Você pode remover animais clicando no botão \"-\".")
                                    .id(9)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(iv_vaca, "Finalizar", "Ao finalizar essa etapa, basta clicar no botão \"Finalizar\" localizado logo abaixo...")
                                    .id(10)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false))
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            iv_vaca.setVisibility(View.GONE);
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 1, 1, 0);
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            switch (lastTarget.id()) {
                                case 1:
                                    iv_vaca.setVisibility(View.GONE);
                                    break;
                                case 6:
                                    iv_vaca.setVisibility(View.VISIBLE);
                                case 7:
                                    iv_vaca.setVisibility(View.GONE);
                                case 9:
                                    iv_vaca.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            iv_vaca.setVisibility(View.GONE);
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 1, 1, 0);
                        }
                    }
            ).start();
        }
    }

    /**
     * Método para verificar se todos os animais contidos na lista são animais completos (dados preenchidos) ou não.
     * Utiliza como base para a verificação somente a categoria.
     *
     * @param lista Lista de animais.
     * @return Verdadeiro caso todos os animais estejam com os campos preenchidos e false caso contrário.
     */
    public boolean checkAnimais(ArrayList<Animais> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCategoria() == null || lista.get(i).getCategoria().length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método responsável por adicionar uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void adicionaLinha() {
        //Infla a linha para a tabela
        TableRow linha_tabela = (TableRow) View.inflate(getActivity(), R.layout.tabela_demanda_linha, null);
        linha_tutorial = linha_tabela;
        criarLinha(linha_tabela);

        //Verifica se os animais já foram carregados. Se não foram, faz o loading.
        if (!load_complete) {
            loadAnimais(linha_tabela);
            calculaTotalAnimais();

            if (numeroDeLinhas + 1 == listaAnimais.size()) {
                load_complete = true; //Carregou todos os animais.
            }
        }

        setListenersLinha(linha_tabela);

        posicaoLinhaTabela++;
        numeroDeLinhas++;

        if (qtdeAnimais.size() < numeroDeLinhas) {
            qtdeAnimais.add(0.0);
        }

        double[] startArray = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        if (matrizUA.size() < numeroDeLinhas) {
            matrizUA.add(startArray);
        }

        if (listaAnimais.size() < numeroDeLinhas) {
            Animais temp = new Animais();
            listaAnimais.add(temp);
        }
    }

    /**
     * Método responsável por remover uma linha no layout e ajustar o tamanho das estruturas que armazenam os dados.
     */
    public void removeLinha() {
        table_layout.removeView(table_layout.getChildAt(posicaoLinhaTabela));

        if (numeroDeLinhas > 0) {
            qtdeAnimais.remove(numeroDeLinhas - 1);
            matrizUA.remove(numeroDeLinhas - 1);
            listaAnimais.remove(numeroDeLinhas - 1);

            posicaoLinhaTabela--;
            numeroDeLinhas--;
        }
    }

    /**
     * Método responsável por adicionar uma linha na tabela demanda atual e configurar o adapter dos spinners.
     */
    private void criarLinha(TableRow linha_tabela) {
        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        final Spinner spinnerCategoria = linha_tabela.findViewById(R.id.spinner_categoria);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        final ArrayAdapter<String> spinnerCategoriaAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoriaAnimal);
        spinnerCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerCategoriaAdapter);

        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        Spinner spinnerMeses = linha_tabela.findViewById(R.id.spinner_meses);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        ArrayAdapter<String> spinnerMesesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayMeses);
        spinnerMesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(spinnerMesesAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);
    }

    /**
     * Método responsável por identificar a posição de um determinado valor dentro do spinner.
     *
     * @param spinner Representa o spinner que contém os valores.
     * @param s       Representa o valor buscado dentro do spinner
     * @return A posição desse valor na lista de valores do spinner.
     */
    private int getSpinnerIndex(Spinner spinner, String s) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)) {
                return i;
            }
        }

        return 0;
    }

    /**
     * Método responsável por carregar todos os animais do banco de dados.
     *
     * @param linha_tabela representa a última linha adicionada.
     */
    public void loadAnimais(TableRow linha_tabela) {
        final Spinner spinnerCategoria = (Spinner) linha_tabela.getChildAt(0);
        final EditText etNumAnimais = (EditText) linha_tabela.getChildAt(1);
        final Spinner spinnerMeses = (Spinner) linha_tabela.getChildAt(2);
        final EditText etPesoInicial = (EditText) linha_tabela.getChildAt(3);
        final EditText etPesoFinal = (EditText) linha_tabela.getChildAt(4);
        final EditText etPesoVer = (EditText) linha_tabela.getChildAt(5);
        final EditText etPesoOut = (EditText) linha_tabela.getChildAt(6);
        final EditText etPesoInv = (EditText) linha_tabela.getChildAt(7);
        final EditText etPesoPrim = (EditText) linha_tabela.getChildAt(8);
        final TextView consumoJan = (TextView) linha_tabela.getChildAt(9);
        final TextView consumoFev = (TextView) linha_tabela.getChildAt(10);
        final TextView consumoMar = (TextView) linha_tabela.getChildAt(11);
        final TextView consumoAbr = (TextView) linha_tabela.getChildAt(12);
        final TextView consumoMai = (TextView) linha_tabela.getChildAt(13);
        final TextView consumoJun = (TextView) linha_tabela.getChildAt(14);
        final TextView consumoJul = (TextView) linha_tabela.getChildAt(15);
        final TextView consumoAgo = (TextView) linha_tabela.getChildAt(16);
        final TextView consumoSet = (TextView) linha_tabela.getChildAt(17);
        final TextView consumoOut = (TextView) linha_tabela.getChildAt(18);
        final TextView consumoNov = (TextView) linha_tabela.getChildAt(19);
        final TextView consumoDez = (TextView) linha_tabela.getChildAt(20);

        spinnerCategoria.setSelection(getSpinnerIndex(spinnerCategoria, listaAnimais.get(numeroDeLinhas).getCategoria()));
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

        if (qtdeAnimais.size() <= numeroDeLinhas) {
            qtdeAnimais.add(listaAnimais.get(numeroDeLinhas).getNumAnimais());
        }
    }

    /**
     * Identifica os elementos da linha, dinamicamente, pelo seu index, e guarda os itens que foram selecionado, no spinner, ou armazenados no editText, para uso posterior
     *
     * @param linha_tabela Representa a linha que foi adicionada na tabela.
     */
    public void setListenersLinha(final TableRow linha_tabela) {
        final Spinner spinnerCategoria = (Spinner) linha_tabela.getChildAt(0);
        final EditText etNumAnimais = (EditText) linha_tabela.getChildAt(1);
        final Spinner spinnerMeses = (Spinner) linha_tabela.getChildAt(2);
        final EditText etPesoInicial = (EditText) linha_tabela.getChildAt(3);
        final EditText etPesoFinal = (EditText) linha_tabela.getChildAt(4);
        final EditText etPesoVer = (EditText) linha_tabela.getChildAt(5);
        final EditText etPesoOut = (EditText) linha_tabela.getChildAt(6);
        final EditText etPesoInv = (EditText) linha_tabela.getChildAt(7);
        final EditText etPesoPrim = (EditText) linha_tabela.getChildAt(8);

        //Spinner categoria de animal.
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoria = spinnerCategoria.getSelectedItem().toString();

                resultadoConsumo = 0;
                //Define o consumo baseado na categoria de animal escolhido.
                switch (categoria) {
                    case "NOVILHOS JOVENS":
                    case "NOVILHAS JOVENS":
                    case "TOUROS": {
                        resultadoConsumo = 2.5;
                        break;
                    }
                    case "NOVILHOS ADULTOS":
                    case "NOVILHAS ADULTOS":
                    case "VACAS (MATRIZES)": {
                        resultadoConsumo = 3;
                        break;
                    }
                    case "BEZERROS":
                    case "BEZERRAS":
                    default: {
                        resultadoConsumo = 2;
                        break;
                    }
                }

                //tv_consumo.setText(String.valueOf(resultadoConsumo));
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //EditText número de animais.
        etNumAnimais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double numeroAnimais = 0.0;
                int posicao = Integer.parseInt(linha_tabela.getTag().toString()) + 1;

                if (verificaVazioET(etNumAnimais)) {
                    numeroAnimais = converteTextoEmDouble(etNumAnimais);
                }
                //Salva no array a quantidade de animais digitada para determinada linha.
                qtdeAnimais.set(posicao, numeroAnimais);

                calculaTotalAnimais();
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Spinner meses.
        spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Edit Text Peso Inicial.
        etPesoInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Edit Text Peso Final.
        etPesoFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Edit Text Peso Verão.
        etPesoVer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Edit text Peso Outono.
        etPesoOut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Edit text Peso inverno.
        etPesoInv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Edit Text peso Primavera.
        etPesoPrim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular(linha_tabela, spinnerCategoria, etNumAnimais, spinnerMeses, etPesoInicial, etPesoFinal, etPesoVer, etPesoOut, etPesoInv, etPesoPrim);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Método responsável por calcular a soma total dos animais e atualizar o valor do TextView.
     */
    public void calculaTotalAnimais() {
        //Calcula o total de animais.
        somaAnimal = 0;

        if (!qtdeAnimais.isEmpty()) {
            for (int i = 0; i < qtdeAnimais.size(); i++) {
                somaAnimal = (int) (somaAnimal + qtdeAnimais.get(i));
            }
        }

        TextView quantidadeAnimal = getView().findViewById(R.id.tv_totalAnimais);
        quantidadeAnimal.setText(String.valueOf(somaAnimal));
    }

    /**
     * Método responsável por ler os valores do spinner e das caixas de texto e realizar os cálculos.
     *
     * @param linha_tabela     Representa a linha sob a qual será realizado os calculos.
     * @param spinnerCategoria Representa o spinner da categoria animal.
     * @param etNumAnimais     Representa a caixa de texto com a quantidade de animais.
     * @param spinnerMeses     Representa o spinner com o mês de entrada do animal na pastagem.
     * @param etPesoInicial    Representa a caixa de texto com o peso inicial do animal.
     * @param etPesoFinal      Representa a caixa de texto com o peso final do animal.
     * @param etPesoVer        Representa a caixa de texto com o ganho de peso do animal no verão.
     * @param etPesoOut        Representa a caixa de texto com o ganho de peso do animal no outono.
     * @param etPesoInv        Representa a caixa de texto com o ganho de peso do animal no inverno.
     * @param etPesoPrim       Representa a caixa de texto com o ganho de peso do animal na primavera.
     */
    public void calcular(TableRow linha_tabela, Spinner spinnerCategoria, EditText etNumAnimais, Spinner spinnerMeses, EditText etPesoInicial, EditText etPesoFinal, EditText etPesoVer, EditText etPesoOut, EditText etPesoInv, EditText etPesoPrim) {
        String categoria;
        double consumo, numeroAnimais, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim;

        if (verificaVazioSP(spinnerCategoria) &&
                verificaVazioET(etNumAnimais) &&
                verificaVazioSP(spinnerMeses) &&
                verificaVazioET(etPesoInicial) &&
                verificaVazioET(etPesoFinal) &&
                verificaVazioET(etPesoVer) &&
                verificaVazioET(etPesoOut) &&
                verificaVazioET(etPesoInv) &&
                verificaVazioET(etPesoPrim)) {

            categoria = spinnerCategoria.getSelectedItem().toString();
            //consumo = Double.parseDouble(textViewConsumo.getText().toString());
            consumo = resultadoConsumo;
            numeroAnimais = converteTextoEmDouble(etNumAnimais);
            pesoInicial = converteTextoEmDouble(etPesoInicial);
            pesoFinal = converteTextoEmDouble(etPesoFinal);
            pesoVer = converteTextoEmDouble(etPesoVer);
            pesoOut = converteTextoEmDouble(etPesoOut);
            pesoInv = converteTextoEmDouble(etPesoInv);
            pesoPrim = converteTextoEmDouble(etPesoPrim);

            double peso_atual;
            double[] pesoVezesQtdeAnimal = new double[12];
            double[] pesos = new double[12];

            String meses = spinnerMeses.getSelectedItem().toString();

            double ganho;
            //Array que mapeia os pesos de acordo com as estações. Cada posição faz referência a um mês.
            double[] ganhoEstacao = new double[]{pesoVer, pesoVer, pesoOut, pesoOut, pesoOut, pesoInv, pesoInv, pesoInv, pesoPrim, pesoPrim, pesoPrim, pesoVer};
            int posicao = 0;  //posicão utilizada para inserir o animal em determinado mês de acordo com a entrada.
            int posicaoLinha = Integer.parseInt(linha_tabela.getTag().toString()) + 1;

            //Define qual a posição que será utilizada no array ganhoEstacao e acordo com o mês de entrada do animal.
            switch (meses) {
                case "Jan":
                    posicao = 0;
                    break;
                case "Fev":
                    posicao = 1;
                    break;
                case "Mar":
                    posicao = 2;
                    break;
                case "Abr":
                    posicao = 3;
                    break;
                case "Mai":
                    posicao = 4;
                    break;
                case "Jun":
                    posicao = 5;
                    break;
                case "Jul":
                    posicao = 6;
                    break;
                case "Ago":
                    posicao = 7;
                    break;
                case "Set":
                    posicao = 8;
                    break;
                case "Out":
                    posicao = 9;
                    break;
                case "Nov":
                    posicao = 10;
                    break;
                case "Dez":
                    posicao = 11;
                    break;
            }

            //Estrutura de repetiçao feita para cada vez que o usuário trocar, o valor de entrada, os campos de textView de meses, limparem.
            for (int i = 9; i < 21; i++) {
                TextView v = (TextView) linha_tabela.getChildAt(i);
                v.setText(String.valueOf(0));
                v.setBackgroundColor(Color.parseColor("#EFEFF1"));
            }

            int cont = 0;
            //Estrutura de repetiçao feita para gerar a sequência de somas que resulta no peso final estipulado pelo usuário.
            for (peso_atual = pesoInicial; peso_atual < pesoFinal; ) {
                int DIAS = 30;
                int KG = 1000;
                ganho = (ganhoEstacao[posicao] * DIAS) / KG;
                peso_atual = (peso_atual + ganho);

                //Mapeia para um array os pesos de cada mês já multiplicados pela quantidade de animais.
                //Esse array serve como entrada para a matriz.
                switch (posicao) {
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

                TextView v = (TextView) linha_tabela.getChildAt(posicao + 9);
                v.setText(doisDecimais.format(peso_atual));
                posicao++;

                //Quando chega no mês de dezembro, dá a volta, vai para janeiro.
                if (posicao > 11) {
                    posicao = 0;
                }

                //Flag para controlar que o número de meses que o animal permanece no campo não seja superior a 12.
                cont++;
                if (cont == 12) {
                    //Toast.makeText(this, "Você extrapolou o limite de 12 meses do animal no campo. Altere os valores de ganhou ou peso final!", Toast.LENGTH_LONG).show();
                    break;
                }

                if(peso_atual >= pesoFinal){
                    v.setBackgroundColor(Color.parseColor("#A9E9CD"));
                }
            }

            matrizUA.set(posicaoLinha, pesoVezesQtdeAnimal);
            calculoUaHa(matrizUA);

            if (listaAnimais.size() >= numeroDeLinhas) {
                Animais animal = new Animais(categoria, consumo, numeroAnimais, meses, pesoInicial, pesoFinal, pesoVer, pesoOut, pesoInv, pesoPrim, pesos);
                listaAnimais.set(posicaoLinha, animal);
            }
        } else {
            //Estrutura de repetiçao feita para cada vez que o usuário trocar, o valor de entrada, os campos de textView de meses, limparem.
            for (int i = 9; i < 21; i++) {
                TextView v = (TextView) linha_tabela.getChildAt(i);
                v.setText(String.valueOf(0));
            }
        }
    }

    /**
     * Método responsável por realizar os cáculos de ua/ha e alterar o valor dos textviews.
     */
    public void calculoUaHa(ArrayList<double[]> matrizUA) {
        //Transforma os totais de cada mês em UA (1 UA = 450KG.).
        ArrayList<Double> listaTotalUA = percorreMatrizESoma(matrizUA);
        for (int i = 0; i < listaTotalUA.size(); i++) {
            listaTotalUA.set(i, (listaTotalUA.get(i) / 450));
        }


        //Recupera a área total vinda da Activity Piquete.
        double areaTotal;
        if (load) {
            Propriedade p = BancoDeDados.propriedadeDAO.getPropriedadeById(id_propriedade);
            areaTotal = p.getArea();
        } else {
            areaTotal = IndexFragment.propriedade.getArea();
        }

        for (int i = 0; i < listaTotalUA.size(); i++) {
            listaTotalUAHA.set(i, Double.parseDouble(doisDecimais.format(listaTotalUA.get(i) / areaTotal).replace(",", ".")));
        }

        TextView totalJan = getView().findViewById(R.id.tv_AreaUaMesJan);
        TextView totalFev = getView().findViewById(R.id.tv_AreaUaMesFev);
        TextView totalMar = getView().findViewById(R.id.tv_AreaUaMesMar);
        TextView totalAbr = getView().findViewById(R.id.tv_AreaUaMesAbr);
        TextView totalMai = getView().findViewById(R.id.tv_AreaUaMesMai);
        TextView totalJun = getView().findViewById(R.id.tv_AreaUaMesJun);
        TextView totalJul = getView().findViewById(R.id.tv_AreaUaMesJul);
        TextView totalAgo = getView().findViewById(R.id.tv_AreaUaMesAgo);
        TextView totalSet = getView().findViewById(R.id.tv_AreaUaMesSet);
        TextView totalOut = getView().findViewById(R.id.tv_AreaUaMesOut);
        TextView totalNov = getView().findViewById(R.id.tv_AreaUaMesNov);
        TextView totalDez = getView().findViewById(R.id.tv_AreaUaMesDez);

        //Altera os valores dos TextViews.
        for (int i = 0; i < listaTotalUAHA.size(); i++) {
            switch (i) {
                case 0: {
                    totalJan.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 1: {
                    totalFev.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 2: {
                    totalMar.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 3: {
                    totalAbr.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 4: {
                    totalMai.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 5: {
                    totalJun.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 6: {
                    totalJul.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 7: {
                    totalAgo.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 8: {
                    totalSet.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 9: {
                    totalOut.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 10: {
                    totalNov.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
                case 11: {
                    totalDez.setText(doisDecimais.format(listaTotalUAHA.get(i)));
                    break;
                }
            }
        }
    }

    /**
     * Método responsável por percorrer a matriz e somar os valores coluna por coluna.
     *
     * @param matriz Representa a matriz com os valores calculados de todos os meses para todas as linhas.
     * @return array com os valores totais por coluna.
     */
    public ArrayList<Double> percorreMatrizESoma(ArrayList<double[]> matriz) {
        //i = linha | j = coluna.
        int i = 0, j = 0;
        double soma = 0.0;

        ArrayList<Double> resultado = new ArrayList<>();
        //Inicializa o array com os totais de cada mês com zero.
        for (int k = 0; k < 12; k++) {
            resultado.add(k, 0.0);
        }

        while (i < matriz.size()) {
            soma = soma + matriz.get(i)[j];

            if ((i + 1) == matriz.size()) {
                i = 0;

                if (j + 1 < 12) {
                    resultado.set(j, soma);
                    j++;
                    soma = 0.0;
                } else {
                    if (j == 11) {
                        resultado.set(j, soma);
                    }
                    break;
                }
            } else {
                i++;
            }
        }
        return resultado;
    }

    /**
     * Método para converter um texto digitado num campo de texto para double.
     *
     * @param et Representa a caixa de texto que contém o texto que será convertido.
     * @return Retorna o texto digitado convertido em double.
     */
    public double converteTextoEmDouble(EditText et) {
        String texto = et.getText().toString();
        double textoEmDouble;

        textoEmDouble = Double.parseDouble(texto);

        return textoEmDouble;
    }

    /**
     * Método para verificar se um Edit Text é vazio.
     *
     * @param et Representa a caixa de texto que será verificada.
     * @return true caso a caixa de texto esteja vazia e false caso contrário.
     */
    public boolean verificaVazioET(EditText et) {
        boolean empty = et.getText().toString().isEmpty();
        return !empty;
    }

    /**
     * Método para verificar se um Spinner é vazio.
     *
     * @param p Representa o spinner que será verificado.
     * @return Retorna true caso o spinner esteja vazio (sem opção selecionada) e false caso contrário.
     */
    public boolean verificaVazioSP(Spinner p) {
        return !p.getSelectedItem().toString().isEmpty();
    }

    public void finalizar() {
        IndexFragment.propriedade.setListaAnimaisAtual(listaAnimais);
        IndexFragment.propriedade.setQtdeAnimais(somaAnimal);
        IndexFragment.listaTotalUAHA = new ArrayList<>(listaTotalUAHA);

        if (IndexActivity.usuario.getId() != -1) {
            BancoDeDados.propriedadeDAO.inserirPropriedade(IndexFragment.propriedade, IndexActivity.usuario.getId());
            id_propriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(IndexFragment.propriedade.getNome());

            if (id_propriedade >= 0) {
                if (IndexFragment.propriedade.getListaPiqueteAtual() != null) {
                    for (int i = 0; i < IndexFragment.propriedade.getListaPiqueteAtual().size(); i++) {
                        BancoDeDados.piqueteDAO.inserirPiquete(IndexFragment.propriedade.getListaPiqueteAtual().get(i), id_propriedade, IPiqueteSchema.TABELA_PIQUETE_ATUAL);
                    }
                }

                if (IndexFragment.listaTotaisMes != null) {
                    BancoDeDados.totalPiqueteMesDAO.inserirTotalMes(IndexFragment.listaTotaisMes, id_propriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
                }

                if (IndexFragment.listaTotaisEstacoes != null) {
                    BancoDeDados.totalPiqueteEstacaoDAO.inserirTotalEstacao(IndexFragment.listaTotaisEstacoes, id_propriedade, ITotalPiqueteEstacao.TABELA_TOTAL_PIQUETE_ESTACAO_ATUAL);
                }

                if (listaAnimais != null) {
                    for (int i = 0; i < listaAnimais.size(); i++) {
                        BancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), id_propriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
                    }
                }

                if (listaTotalUAHA != null) {
                    BancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, id_propriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);
                }
            }
        }

        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
    }

    public void atualizarDados(ArrayList<Animais> listaAnimais, int idPropriedade) {
        if (modo.equals("atual")) {
            //Deleta os valores antigos.
            BancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
            BancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

            //Salva os novos valores.
            for (int i = 0; i < listaAnimais.size(); i++) {
                BancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_ATUAL);
            }

            BancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

            //Atualiza a qtde de animais na tabela de propriedades.
            BancoDeDados.propriedadeDAO.updatePropriedade(idPropriedade, somaAnimal);
        } else if (modo.equals("proposta")) {
            //Deleta os valores antigos.
            BancoDeDados.animaisDAO.deleteAnimalByPropId(idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);
            BancoDeDados.totalAnimaisDAO.deleteTotalAnimaisByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);

            //Salva os novos valores.
            for (int i = 0; i < listaAnimais.size(); i++) {
                BancoDeDados.animaisDAO.inserirAnimal(listaAnimais.get(i), idPropriedade, IAnimaisSchema.TABELA_ANIMAIS_PROPOSTA);
            }

            BancoDeDados.totalAnimaisDAO.inserirTotalAnimal(listaTotalUAHA, idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);

            //Atualiza a qtde de animais na tabela de propriedades.
            BancoDeDados.propriedadeDAO.updatePropriedade(idPropriedade, somaAnimal);
        }


        Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Método chamado quando o Fragment é pausado, exemplo: quando o AnimaisFragment é mostrado ou quando troca-se de aba no VerDadosFragment.
     */
    @Override
    public void onPause() {
        super.onPause();

        saved_table_layout = new TableLayout(getActivity());

        while (table_layout.getChildCount() > 0) {
            TableRow linha = (TableRow) table_layout.getChildAt(0);
            table_layout.removeViewAt(0);
            saved_table_layout.addView(linha);
        }
    }

    /**
     * Método chamado toda vez que o fragment é mostrado.
     * Utilizado para recuperar o estado da tabela.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (saved_table_layout != null) {
            numeroDeLinhas = saved_table_layout.getChildCount();

            while (saved_table_layout.getChildCount() > 0) {
                TableRow linha = (TableRow) saved_table_layout.getChildAt(0);
                saved_table_layout.removeViewAt(0);
                table_layout.addView(linha);
            }
        }
    }
}