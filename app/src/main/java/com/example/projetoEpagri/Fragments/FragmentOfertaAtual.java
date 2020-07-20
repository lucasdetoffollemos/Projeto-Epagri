package com.example.projetoEpagri.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.projetoEpagri.R;



public class FragmentOfertaAtual extends Fragment {


    Button bt_adicionar_linha, bt_remover_linha;
    int i=0;
    TableRow linha_tabela;
    TableLayout table_layout;


    public FragmentOfertaAtual() {
        // Required empty public constructor
    }

    /**
     * Aqui é inflado o arquivo xml do fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_oferta_atual, container, false);
    }

    /**
     * Nesse método é colocado os códigos no fragmento
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Your code here

        table_layout = (TableLayout) getView().findViewById(R.id.table_layout);

        bt_adicionar_linha = getView().findViewById(R.id.bt_adicionar_linha);
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linha_tabela = (TableRow) View.inflate(getContext(), R.layout.tabela_oferta_atual_linha, null);
                //set tag for each TableRow
                linha_tabela.setTag(i);
                i++;
                //add TableRows to TableLayout
                table_layout.addView(linha_tabela);

            }
        });

        bt_remover_linha = getView().findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(i));
                i--;
            }
        });

    }
}
