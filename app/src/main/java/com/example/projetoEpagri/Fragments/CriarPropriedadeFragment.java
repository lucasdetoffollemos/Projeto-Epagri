package com.example.projetoEpagri.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

public class CriarPropriedadeFragment extends Fragment {
    private String nome_propriedade, regiao;

    public CriarPropriedadeFragment() {}

    public static CriarPropriedadeFragment newInstance() {
        CriarPropriedadeFragment fragment = new CriarPropriedadeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return inflater.inflate(R.layout.fragment_criar_propriedade, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final EditText et_nome_propriedade = getView().findViewById(R.id.et_nomePropriedade);
        final ImageView iv_mapa = getView().findViewById(R.id.iv_map);
        final TextView tv_texto_clima = getView().findViewById(R.id.tv_texto_clima);
        final TextView tv_clima = getView().findViewById(R.id.tv_clima);
        final Button bt_proximo = getView().findViewById(R.id.bt_levaPiquete);

        iv_mapa.setTag(R.drawable.img_mapa_sul_branco);
        tv_clima.setText("Clima Quente - cfa");
        regiao = "cfa";

        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IndexFragment.propriedade != null){
                    IndexFragment.propriedade = null;
                }

                getFragmentManager().popBackStack();
            }
        });

        iv_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = (Integer) iv_mapa.getTag();

                switch(id) {
                    case R.drawable.img_mapa_sul_branco:
                        iv_mapa.setImageResource(R.drawable.img_mapa_sul_cfb);
                        iv_mapa.setTag(R.drawable.img_mapa_sul_cfb);
                        regiao = "cfb";
                        tv_clima.setText("Clima Frio - cfb");
                        break;
                    case R.drawable.img_mapa_sul_cfb:
                        iv_mapa.setImageResource(R.drawable.img_mapa_sul_branco);
                        iv_mapa.setTag(R.drawable.img_mapa_sul_branco);
                        regiao = "cfa";
                        tv_clima.setText("Clima Quente - cfa");

                        break;
                    //TODO mapa sul cfb.
                    default:
                        break;
                }

            }
        });

        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome_propriedade = et_nome_propriedade.getText().toString().trim();

                //Adicionar verificação do clima quando tiver os mapas corretos.
                if(!nome_propriedade.isEmpty()){
                    IndexFragment.propriedade = new Propriedade(nome_propriedade, regiao);

                    Fragment piquete_fragment = PiqueteFragment.newInstance();
                    MainActivity.startFragment(piquete_fragment, "piquete_fragment", R.id.ll_index, true, true, getActivity());
                }
                else{
                    Toast.makeText(getActivity(), "Insira o nome da propriedade", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}