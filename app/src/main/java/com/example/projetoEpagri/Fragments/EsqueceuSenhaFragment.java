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
import android.widget.Toast;

import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

public class EsqueceuSenhaFragment extends Fragment {

    public EsqueceuSenhaFragment() {}

    public static EsqueceuSenhaFragment newInstance() {
        EsqueceuSenhaFragment fragment = new EsqueceuSenhaFragment();
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
        return inflater.inflate(R.layout.fragment_esqueceu_senha, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View toolbar = getView().findViewById(R.id.included_toolbar);
        final ImageView iv_voltar = toolbar.findViewById(R.id.iv_voltar);
        final EditText et_email = getView().findViewById(R.id.et_email);
        final Button bt_modifica = getView().findViewById(R.id.bt_confirmar_email);

        //Clique no botão "voltar".
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Clique no botão "Confirmar".
        bt_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String email = et_email.getText().toString().trim();
                verificaEmail(email);
            }
        });
    }

    /**
     *  Método para verificar se o email do usuário existe,
     *  se existir leva para outra página para criação da nova senha, se não existir apenas apresenta um toast.
     */
    public void verificaEmail(String email) {
        Boolean usuario_check = BancoDeDados.usuarioDAO.verificaEmailUsuario(email);

        if(usuario_check){
            Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(email);

            Fragment criar_senha_fragment = CriarSenhaFragment.newInstance(usuario.getNome(), usuario.getSenha());
            MainActivity.startFragment(criar_senha_fragment, "criar_senha_fragment", R.id.ll_main, true, true, getActivity());
        }
        else{
            Toast.makeText(getActivity(), "Email não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
}