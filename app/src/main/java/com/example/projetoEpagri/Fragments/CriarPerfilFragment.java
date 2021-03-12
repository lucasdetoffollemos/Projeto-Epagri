package com.example.projetoEpagri.Fragments;

import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

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
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.Timer;
import java.util.TimerTask;

public class CriarPerfilFragment extends Fragment {
    private String nome_usuario;

    public CriarPerfilFragment() {}

    public static CriarPerfilFragment newInstance() {
        CriarPerfilFragment fragment = new CriarPerfilFragment();
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
        return inflater.inflate(R.layout.fragment_criar_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_voltar = getView().findViewById(R.id.iv_voltar);
        final EditText et_nome = getView().findViewById(R.id.et_nome);
        final EditText et_email = getView().findViewById(R.id.et_email);
        final EditText et_telefone = getView().findViewById(R.id.et_telefone);
        final EditText et_senha = getView().findViewById(R.id.et_senha);
        final Button bt_criar = getView().findViewById(R.id.bt_criar);

        //Clique no botão "voltar".
        iv_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Clique no botão "Criar".
        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, telefone, senha;
                nome_usuario = et_nome.getText().toString().trim();  //trim remove espaços em branco antes e após a palavra.
                email = et_email.getText().toString().trim();
                telefone = et_telefone.getText().toString().trim();
                senha = et_senha.getText().toString().trim();

                if(nome_usuario.length() > 0 && email.length() > 0 && telefone.length() > 0 && senha.length() > 0 ){
                    criarPerfil(nome_usuario, email, telefone, senha);
                }
                else{
                    Toast.makeText(getActivity(), "Por favor, preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método responsável por criar um usuário e salvar no banco de dados.
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        BancoDeDados.usuarioDAO.inserirUsuario(u);
        Toast.makeText(getActivity(), "Usuario criado com sucesso! ", Toast.LENGTH_SHORT).show();


        //O código abaixo aguarda 2 seg e redireciona o usuário para o IndexFragment.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivityIndex(nome_usuario);
            }
        }, 1000);
    }

    /**
     * Método responsável por iniciar a ActivityIndex.
     * @param nome_usuario
     */
    public void startActivityIndex(String nome_usuario){
        //Remove o CriarPerfrilFragment da pilha.
        getFragmentManager().popBackStack();

        Intent i = new Intent(getActivity(), IndexActivity.class);
        i.putExtra("nome_usuario", nome_usuario);
        startActivity(i);
    }
}