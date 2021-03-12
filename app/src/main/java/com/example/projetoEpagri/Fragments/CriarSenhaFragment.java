package com.example.projetoEpagri.Fragments;

import android.app.AlertDialog;

import android.content.DialogInterface;

import android.content.Intent;
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

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;

import java.util.Timer;
import java.util.TimerTask;

public class CriarSenhaFragment extends Fragment {
    private static final String ARG_PARAM1 = "nome_usuario";
    private static final String ARG_PARAM2 = "senha_usuario";

    private String nome_usuario, senha_usuario;
    private int id_usuario;

    public CriarSenhaFragment() {}

    public static CriarSenhaFragment newInstance(String nome, String senha) {
        CriarSenhaFragment fragment = new CriarSenhaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nome);
        args.putString(ARG_PARAM2, senha);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nome_usuario = getArguments().getString(ARG_PARAM1);
            senha_usuario = getArguments().getString(ARG_PARAM2);

            id_usuario = BancoDeDados.usuarioDAO.getUSuarioId(nome_usuario);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return inflater.inflate(R.layout.fragment_criar_senha, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final EditText et_senha = getView().findViewById(R.id.et_senha);
        final EditText et_senha_repeat = getView().findViewById(R.id.et_senha_repeat);
        final Button bt_confirmar = getView().findViewById(R.id.bt_confirmar_senha);

        //Clique no botão "voltar".
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Clique no botão "Confirmar".
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String senha = et_senha.getText().toString().trim();
                String senha_repeat = et_senha_repeat.getText().toString().trim();

                if(senha.equals(senha_repeat)){
                    modificarSenha(senha);
                }
                else{
                    Toast.makeText(getActivity(), "As senhas digitadas não conferem!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método para alterar a senha do usuário.
     */
    public void modificarSenha(final String senha) {
        //Verifica se a senha ja existe.
        if(senha_usuario.equals(senha)) {
            Toast.makeText(getActivity(), "A nova senha precisa ser diferente da senha atual!", Toast.LENGTH_SHORT).show();
        }else{
            //Cria a caixa de confirmação para verificar se o usuário quer ou não alterar sua senha.
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Alterar senha");
            builder.setMessage( "Tem certeza que deseja alterar sua senha?" );
            builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BancoDeDados.usuarioDAO.updateUsuarioId(id_usuario, senha);
                    Toast.makeText(getActivity(), "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();

                    //Redireciona o usuário para o IndexFragment após 2 seg.
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivityIndex(nome_usuario);
                        }
                    }, 1500);
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
    }

    /**
     * Método responsável por iniciar a ActivityIndex.
     * @param nome_usuario Nome do usuário.
     */
    public void startActivityIndex(String nome_usuario){
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();

        Intent i = new Intent(getActivity(), IndexActivity.class);
        i.putExtra("nome_usuario", nome_usuario);
        startActivity(i);
    }
}