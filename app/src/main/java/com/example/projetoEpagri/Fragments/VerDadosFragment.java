package com.example.projetoEpagri.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.R;
import com.google.android.material.tabs.TabLayout;

public class VerDadosFragment extends Fragment {
    private static final String ARG_PARAM1 = "nome_propriedade";
    private static final String ARG_PARAM2 =  "fazer_proposta";

    private int id_propriedade;
    private boolean fazer_proposta; //Flag para indicar se o Fragment está sendo aberto no modo "Fazer Proposta".
    private boolean click_bt_atualizar;

    public VerDadosFragment() {}

    public static VerDadosFragment newInstance(String nome_propr, boolean fazer_proposta) {
        VerDadosFragment fragment = new VerDadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nome_propr);
        args.putBoolean(ARG_PARAM2,fazer_proposta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String nome_propriedade = getArguments().getString(ARG_PARAM1);
            fazer_proposta = getArguments().getBoolean(ARG_PARAM2);
            id_propriedade = MainActivity.bancoDeDados.propriedadeDAO.getPropriedadeId(nome_propriedade);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if(fazer_proposta){
            return inflater.inflate(R.layout.fragment_fazer_proposta, container, false);
        }
        else{
            return inflater.inflate(R.layout.fragment_ver_dados, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View toolbar = getView().findViewById(R.id.included_toolbar);
        final ImageView iv_voltar = toolbar.findViewById(R.id.iv_voltar);
        final TabLayout tab_layout = getView().findViewById(R.id.tab_layout);

        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criando a caixa de pergunta, se o usuário quer ou não sair do "Ver Dados"
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Atenção");
                builder.setMessage("Você deve clicar no botão \"Atualizar Dados\" antes de sair ou todas as alterações realizadas serão perdidas. Tem certeza que deseja sair?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                    }
                });


                builder.setNeutralButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.show();
            }
        });

        final Fragment oferta_atual_fragment = PiqueteFragment.newInstance(id_propriedade, true, "atual");
        final Fragment demanda_atual_fragment = AnimaisFragment.newInstance(id_propriedade, true, "atual");
        final Fragment oferta_proposta_fragment = PiqueteFragment.newInstance(id_propriedade, true, "proposta");
        final Fragment demanda_proposta_fragment = AnimaisFragment.newInstance(id_propriedade, true, "proposta");

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posicao = tab_layout.getSelectedTabPosition();
                int id = R.id.ll_to_load_fragment;

                if(!fazer_proposta){
                    switch (posicao){
                        case 0:
                            carregaFragment(oferta_atual_fragment, id, "oferta_atual_fragment");
                            break;
                        case 1:
                            carregaFragment(demanda_atual_fragment, id, "demanda_atual_fragment");
                            break;
                        case 2:
                            carregaFragment(oferta_proposta_fragment, id, "oferta_proposta_fragment");
                            break;
                        case 3:
                            carregaFragment(demanda_proposta_fragment, id, "demanda_proposta_fragment");
                            break;
                        default:
                    }
                }
                else{
                    switch (posicao){
                        case 0:
                            carregaFragment(oferta_proposta_fragment, id, "oferta_proposta_fragment");
                            break;
                        case 1:
                            carregaFragment(demanda_proposta_fragment, id, "demanda_proposta_fragment");
                            break;
                        default:
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        if (fazer_proposta){
            carregaFragment(oferta_proposta_fragment, R.id.ll_to_load_fragment, "oferta_proposta_fragment");
        }
        else{
            carregaFragment(oferta_atual_fragment, R.id.ll_to_load_fragment, "oferta_atual_fragment");
        }
    }

    public void carregaFragment(Fragment fragment, int id, String tag){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}