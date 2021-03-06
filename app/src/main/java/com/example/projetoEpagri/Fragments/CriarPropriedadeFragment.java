package com.example.projetoEpagri.Fragments;

import android.annotation.SuppressLint;
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

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.Classes.Tutorial;
import com.example.projetoEpagri.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

public class CriarPropriedadeFragment extends Fragment {
    private String nome_propriedade, regiao;

    public CriarPropriedadeFragment() {
    }

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

        final View toolbar = getView().findViewById(R.id.included_toolbar);
        final ImageView iv_voltar = toolbar.findViewById(R.id.iv_voltar);
        final ImageView iv_vaca = getView().findViewById(R.id.iv_vaca);
        final EditText et_nome_propriedade = getView().findViewById(R.id.et_nomePropriedade);
        final ImageView iv_mapa = getView().findViewById(R.id.iv_map);
        final ImageView iv_cfb = getView().findViewById(R.id.iv_cfb);
        final TextView tv_clima = getView().findViewById(R.id.tv_clima);
        final Button bt_proximo = getView().findViewById(R.id.bt_levaPiquete);

        TextView toolbar_title = toolbar.findViewById(R.id.tv_titulo_toolbar);
        toolbar_title.setText(R.string.txt_bt_cadastroPropriedade);

        iv_mapa.setTag(R.drawable.img_mapa_sul_branco);
        tv_clima.setText(R.string.txt_tv_clima);
        regiao = "";

        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IndexFragment.propriedade != null) {
                    IndexFragment.propriedade = null;
                }

                getFragmentManager().popBackStack();
            }
        });

        iv_mapa.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                Integer id = (Integer) iv_mapa.getTag();

                switch (id) {
                    case R.drawable.img_mapa_sul_branco:
                        iv_mapa.setImageResource(R.drawable.mapa_cfa);
                        iv_mapa.setTag(R.drawable.mapa_cfa);
                        regiao = "cfa";
                        tv_clima.setText(R.string.txt_tv_config_cfa);
                        break;
                    case R.drawable.mapa_cfb:
                        iv_mapa.setImageResource(R.drawable.mapa_cfa);
                        iv_mapa.setTag(R.drawable.mapa_cfa);
                        regiao = "cfa";
                        tv_clima.setText(R.string.txt_tv_config_cfa);
                        break;
                    default:
                        break;
                }

            }
        });

        iv_cfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = (Integer) iv_mapa.getTag();

                switch (id) {
                    case R.drawable.img_mapa_sul_branco:
                        iv_mapa.setImageResource(R.drawable.mapa_cfb);
                        iv_mapa.setTag(R.drawable.mapa_cfb);
                        regiao = "cfb";
                        tv_clima.setText(R.string.txt_tv_config_cfb);
                        break;
                    case R.drawable.mapa_cfa:
                        iv_mapa.setImageResource(R.drawable.mapa_cfb);
                        iv_mapa.setTag(R.drawable.mapa_cfb);
                        regiao = "cfb";
                        tv_clima.setText(R.string.txt_tv_config_cfb);
                        break;
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
                if (!nome_propriedade.isEmpty() && regiao.length() > 0) {
                    IndexFragment.propriedade = new Propriedade(nome_propriedade, regiao);

                    Fragment piquete_fragment = PiqueteFragment.newInstance(-1, false, null);
                    MainActivity.startFragment(piquete_fragment, "piquete_fragment", R.id.ll_index, true, true, getActivity());
                } else {
                    Toast.makeText(getActivity(), "Insira o nome da propriedade e selecione a localidade no mapa antes de prosseguir!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        IndexFragment.tutorial = MainActivity.bancoDeDados.tutorialDAO.getTutorial();

        if (IndexFragment.tutorial != null && IndexFragment.tutorial.getCriar_propriedade() == 0) {
            iv_vaca.setVisibility(View.VISIBLE);

            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(iv_vaca, "Etapa 1", "Essa é a primeira etapa do cadastro de propriedades.\n\nDigite o nome da propriedade no campo de texto e indique no mapa a localização da propriedade.\n\nAo terminar, basta clicar no botão \"Próximo Passo\" logo abaixo...")
                                    .id(1)
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
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 0, 0, 0);
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            switch (lastTarget.id()) {
                                case 1:
                                    iv_vaca.setVisibility(View.GONE);
                                    break;
                            }
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            iv_vaca.setVisibility(View.GONE);
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 0, 0, 0);
                        }
                    }
            ).start();
        }
    }
}