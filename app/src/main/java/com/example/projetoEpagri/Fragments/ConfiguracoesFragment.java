package com.example.projetoEpagri.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Dados;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class ConfiguracoesFragment extends Fragment {

    private static final String ARG_PARAM1 = "dados";

    private String dados;
    private ArrayList<Dados> lista_dados;
    private TableLayout table_layout;
    private int posicaoLinhaTabela=-1, numeroDeLinhas = 0;

    public ConfiguracoesFragment() {}

    public static ConfiguracoesFragment newInstance(String dados) {
        ConfiguracoesFragment fragment = new ConfiguracoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, dados);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dados = getArguments().getString(ARG_PARAM1);
            lista_dados = BancoDeDados.dadosDAO.getPastagem(dados);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.fragment_configuracoes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final View layout_incluido_tabela_oferta_2 = getView().findViewById(R.id.included_layout_tabela_oferta_2);
        final ScrollView scrollView = getView().findViewById(R.id.sv_edit);
        final Button bt_atualizar = getView().findViewById(R.id.bt_edit_atualizar);
        final TextView titulo_tabela = layout_incluido_tabela_oferta_2.findViewById(R.id.tv_prodEstimadaMes);
        table_layout = getView().findViewById(R.id.tl_edit_cf);
        titulo_tabela.setText(getResources().getString(R.string.txt_tv_titulo_tabela_edit));

        for(int i=0; i<lista_dados.size(); i++){
            //Infla a linha para a tabela
            adicionaLinha();
        }

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);

        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Previne que o scroll faça a rolagem automática para o edittext que possui o foco.
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestFocusFromTouch();
                return false;
            }
        });

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("ATENÇÃO");
                builder.setMessage( "Atualizar os dados afetará os calculos realizados pelo aplicativo. Tem certeza que deseja continuar?" );
                builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BancoDeDados.dadosDAO.deleteAllDados(dados);

                        for(int i=0; i<lista_dados.size(); i++){
                            BancoDeDados.dadosDAO.inserirPastagem(
                                    lista_dados.get(i).getPastagem(),
                                    lista_dados.get(i).getCondicao(0),
                                    lista_dados.get(i).getCondicao(1),
                                    lista_dados.get(i).getCondicao(2),
                                    lista_dados.get(i).getMeses(),
                                    lista_dados.get(i).getTotal(),
                                    dados);
                        }

                        Toast.makeText(getActivity(), "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton(" NÃO ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
    }

    /**
     * Método responsável por adicionar uma linha na tabela.
     */
    public void adicionaLinha(){
        //Infla a linha para a tabela
        TableRow linha_tabela = (TableRow) View.inflate(getActivity(), R.layout.tabela_edit_cf_linha, null);
        criarLinha(linha_tabela);
        loadDadosAndSetListeners(linha_tabela);

        posicaoLinhaTabela++; //O indica a posição da linha dentro do TableView (primeira posição = -1)
        numeroDeLinhas++;
    }

    private void criarLinha(TableRow linha_tabela){
        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(posicaoLinhaTabela);
        table_layout.addView(linha_tabela);
    }

    /**
     * Método utilizado para carregar os dados da tabela cfa ou cfb.
     * @param linha_tabela Representa a linha com os dados recuperados do banco de dados.
     */
    public void loadDadosAndSetListeners(final TableRow linha_tabela){
        final EditText pastagem = (EditText) linha_tabela.getChildAt(0);
        final EditText condDegradada = (EditText) linha_tabela.getChildAt(1);
        final EditText condMedia = (EditText) linha_tabela.getChildAt(2);
        final EditText condOtima = (EditText) linha_tabela.getChildAt(3);
        final EditText prodJan = (EditText) linha_tabela.getChildAt(4);
        final EditText prodFev = (EditText) linha_tabela.getChildAt(5);
        final EditText prodMar = (EditText) linha_tabela.getChildAt(6);
        final EditText prodAbr = (EditText) linha_tabela.getChildAt(7);
        final EditText prodMai = (EditText) linha_tabela.getChildAt(8);
        final EditText prodJun = (EditText) linha_tabela.getChildAt(9);
        final EditText prodJul = (EditText) linha_tabela.getChildAt(10);
        final EditText prodAgo = (EditText) linha_tabela.getChildAt(11);
        final EditText prodSet = (EditText) linha_tabela.getChildAt(12);
        final EditText prodOut = (EditText) linha_tabela.getChildAt(13);
        final EditText prodNov = (EditText) linha_tabela.getChildAt(14);
        final EditText prodDez = (EditText) linha_tabela.getChildAt(15);
        final EditText total = (EditText) linha_tabela.getChildAt(16);

        pastagem.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getPastagem()));
        condDegradada.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getCondicao(0)));
        condMedia.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getCondicao(1)));
        condOtima.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getCondicao(2)));
        prodJan.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(0)));
        prodFev.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(1)));
        prodMar.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(2)));
        prodAbr.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(3)));
        prodMai.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(4)));
        prodJun.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(5)));
        prodJul.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(6)));
        prodAgo.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(7)));
        prodSet.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(8)));
        prodOut.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(9)));
        prodNov.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(10)));
        prodDez.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getMeses(11)));
        total.setText(String.valueOf(lista_dados.get(numeroDeLinhas).getTotal()));

        pastagem.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 0, pastagem.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        condDegradada.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 1, condDegradada.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        condMedia.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 2, condMedia.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        condOtima.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela,3, condOtima.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodJan.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 4, prodJan.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodFev.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 5, prodFev.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodMar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 6, prodMar.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodAbr.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 7, prodAbr.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodMai.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 8, prodMai.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodJun.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 9, prodJun.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodJul.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 10, prodJul.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodAgo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 11, prodAgo.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodSet.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 12, prodSet.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodOut.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 13, prodOut.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodNov.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 14, prodNov.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        prodDez.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 15, prodDez.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        total.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { edit(linha_tabela, 16, total.getText().toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Método utilizado para alterar o valor na lista de dados conforme o usuário digita novos valores nos edittexts.
     * @param linha_tabela Representa a linha da tabela. Utilizada aqui para identificar a posição da linha que está sendo editada.
     * @param posCompDentroDaLinha Representa qual a posição do edittext que está sendo editado (dentro da linha).
     * @param valor Representa o valor digitado no edittext.
     */
    public void edit(TableRow linha_tabela, int posCompDentroDaLinha, String valor){
        int posicao = Integer.parseInt(linha_tabela.getTag().toString())+1;
        Dados d = lista_dados.get(posicao);
        double v;

        if(valor.length() > 0){
            switch (posCompDentroDaLinha){
                case 0:
                    d.setPastagem(valor);
                    break;
                case 1:
                    v = Double.parseDouble(valor);
                    d.setCondicao(v, 0);
                    break;
                case 2:
                    v = Double.parseDouble(valor);
                    d.setCondicao(v, 1);
                    break;
                case 3:
                    v = Double.parseDouble(valor);
                    d.setCondicao(v, 2);
                    break;
                case 4:
                    d.setMeses(Integer.parseInt(valor), 0);
                    break;
                case 5:
                    d.setMeses(Integer.parseInt(valor), 1);
                    break;
                case 6:
                    d.setMeses(Integer.parseInt(valor), 2);
                    break;
                case 7:
                    d.setMeses(Integer.parseInt(valor), 3);
                    break;
                case 8:
                    d.setMeses(Integer.parseInt(valor), 4);
                    break;
                case 9:
                    d.setMeses(Integer.parseInt(valor), 5);
                    break;
                case 10:
                    d.setMeses(Integer.parseInt(valor), 6);
                    break;
                case 11:
                    d.setMeses(Integer.parseInt(valor), 7);
                    break;
                case 12:
                    d.setMeses(Integer.parseInt(valor), 8);
                    break;
                case 13:
                    d.setMeses(Integer.parseInt(valor), 9);
                    break;
                case 14:
                    d.setMeses(Integer.parseInt(valor), 10);
                    break;
                case 15:
                    d.setMeses(Integer.parseInt(valor), 11);
                    break;
                case 16:
                    d.setTotal(Integer.parseInt(valor));
                    break;
            }
        }

        lista_dados.set(posicao, d);
    }
}