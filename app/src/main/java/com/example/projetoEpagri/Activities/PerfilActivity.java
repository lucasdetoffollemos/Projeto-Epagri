package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.Dao.UsuarioDAO;

import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {
    private EditText et_nome, et_email, et_telefone, et_senha;
    private UsuarioDAO dao;

    public Button bt_criar, bt_deletar, bt_listar;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializa();
        setListener();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        et_nome = findViewById(R.id.et_nome);
        et_email = findViewById(R.id.et_email);
        et_telefone = findViewById(R.id.et_telefone);
        et_senha = findViewById(R.id.et_senha);
        bt_criar = findViewById(R.id.bt_criar);
        bt_listar = findViewById(R.id.bt_listar);
        bt_deletar = findViewById(R.id.bt_deletar);

        dao = new UsuarioDAO(this);
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListener(){
        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, email, telefone, senha;
                nome = et_nome.getText().toString();
                email = et_email.getText().toString();
                telefone = et_telefone.getText().toString();
                senha = et_senha.getText().toString();

                criarPerfil(nome, email, telefone, senha);
            }
        });

        bt_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });

        bt_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarTodosUsuario();
            }
        });
    }

    /**
     * Método executado ao clicar no botão "criar" na tela Perfil.
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        long id = dao.inserirUsuario(u);
        Toast.makeText(this, "Usuario inserido com id " + id, Toast.LENGTH_SHORT).show();
        //O código abaixo da um tempo da 2 seg até voltar a outra página.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
        limparDados();
    }

    /**
     *Método criado para levar a página, onde são criados os usuários
     */
    private void listarUsuarios() {
        Intent i = new Intent(PerfilActivity.this, ListaUsuariosActivity.class);
        startActivity(i);
    }

    /**
     * Método responsavel por deletar todos os usuário da lista.
     */
    public void deletarTodosUsuario(){
        dao.excluiTodosUsuarios();
        Toast.makeText(this, "Usuários deletados", Toast.LENGTH_SHORT).show();
        //limparDados();
    }

    /**
     * Método responsável por limpar os dados do formulário de login.
     */
    private void limparDados() {
        et_nome.setText("");
        et_email.setText("");
        et_telefone.setText("");
        et_senha.setText("");
    }
}
