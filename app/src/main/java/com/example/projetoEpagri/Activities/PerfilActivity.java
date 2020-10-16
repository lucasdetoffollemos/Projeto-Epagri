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

import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {
    private EditText et_nome, et_email, et_telefone, et_senha;
    public Button bt_criar, bt_atualizar, bt_deletar, bt_listar;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializa();
        setListener();
    }

    /**
     * Método responsável por inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        this.et_nome = findViewById(R.id.et_nome);
        this.et_email = findViewById(R.id.et_email);
        this.et_telefone = findViewById(R.id.et_telefone);
        this.et_senha = findViewById(R.id.et_senha);
        this.bt_criar = findViewById(R.id.bt_criar);
        this.bt_atualizar = findViewById(R.id.bt_atualizar);
        this.bt_listar = findViewById(R.id.bt_listar);
        this.bt_deletar = findViewById(R.id.bt_deletar);
    }

    /**
     * Método responsável por setar os listener dos botões e tudo mais que for clicável na tela de perfil.
     */
    public void setListener(){
        this.bt_criar.setOnClickListener(new View.OnClickListener() {
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

        this.bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, email, telefone, senha;
                nome = et_nome.getText().toString();
                email = et_email.getText().toString();
                telefone = et_telefone.getText().toString();
                senha = et_senha.getText().toString();

                LoginActivity.bancoDeDados.usuarioDAO.updateUsuario(1, nome, email, telefone, senha);
            }
        });

        this.bt_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });

        this.bt_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarTodosUsuario();
            }
        });
    }

    /**
     * Método responsável por criar um usuário e salvar no banco de dados.
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        long id = LoginActivity.bancoDeDados.usuarioDAO.inserirUsuario(u);
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
     * Método responsável por mostrar a tela de listagem dos usuários.
     */
    private void listarUsuarios() {
        Intent i = new Intent(PerfilActivity.this, ListaUsuariosActivity.class);
        startActivity(i);
    }

    /**
     * Método responsável por deletar todos os usuário da lista.
     */
    public void deletarTodosUsuario(){
        LoginActivity.bancoDeDados.usuarioDAO.deleteAllUsuarios();
        Toast.makeText(this, "Usuários deletados", Toast.LENGTH_SHORT).show();
    }

    /**
     * Método responsável por limpar os dados do formulário de login.
     */
    private void limparDados() {
        this.et_nome.setText("");
        this.et_email.setText("");
        this.et_telefone.setText("");
        this.et_senha.setText("");
    }
}
