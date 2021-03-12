package com.example.projetoEpagri.Fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;

public class LoginFragment extends Fragment {
    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv_criar_perfil, tv_recuperar_senha;
        final EditText et_nome, et_senha;
        Button bt_login;

        et_nome = getView().findViewById(R.id.et_nome);
        et_senha = getView().findViewById(R.id.et_senha);
        bt_login = getView().findViewById(R.id.bt_login);
        tv_criar_perfil = getView().findViewById(R.id.tv_criarPerfil);
        tv_recuperar_senha = getView().findViewById(R.id.tv_recuperarSenha);

        //Clique no botão "Login".
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = et_nome.getText().toString();
                String senha = et_senha.getText().toString();

                login(nome, senha);

                et_nome.setText("");
                et_senha.setText("");
                et_nome.clearFocus();
                et_senha.clearFocus();
            }
        });

        //Clique no texto "Criar Perfil".
        tv_criar_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment criar_perfil_fragment = CriarPerfilFragment.newInstance();
                MainActivity.startFragment(criar_perfil_fragment, "criar_perfil_fragment", R.id.ll_main, true, true, getActivity());
            }
        });

        //Clique no texto "Esqueceu sua senha?"
        tv_recuperar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment esqueceu_senha_fragment = EsqueceuSenhaFragment.newInstance();
                MainActivity.startFragment(esqueceu_senha_fragment, "esqueceu_senha_fragment", R.id.ll_main, true, true, getActivity());
            }
        });
    }

    /**
     * Método responsável por verificar o nome e senha do usuário e realizar o login.
     * @param nome Nome do usuário.
     * @param senha Senha do usuário.
     */
    public void login(String nome, String senha) {
        boolean check_email_senha =  BancoDeDados.usuarioDAO.login(nome, senha);

        if(check_email_senha){
            Toast.makeText(getActivity(), "Usuário logado", Toast.LENGTH_SHORT).show();
            startActivityIndex(nome);
        }
        else if(nome.isEmpty() || senha.isEmpty()){
            Toast.makeText(getActivity(), "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Nome de usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método responsável por iniciar a ActivityIndex.
     * @param nome_usuario Nome do usuário.
     */
    public void startActivityIndex(String nome_usuario){
        Intent i = new Intent(getActivity(), IndexActivity.class);
        i.putExtra("nome_usuario", nome_usuario);
        startActivity(i);
    }
}