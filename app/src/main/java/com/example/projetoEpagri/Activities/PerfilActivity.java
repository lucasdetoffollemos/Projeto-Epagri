package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Classes.DadosSul;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.Dao.UsuarioDAO;

import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {
    /**
     * Criando atributos para conectar com os id's do activity_perfil
     */
    private EditText et_nome, et_email, et_telefone, et_senha;
    private UsuarioDAO dao;
    //////////////////////
    public Button bt_voltar, bt_criar, bt_deletar, bt_listar;
    ////////////////////
    private DadosSulDAO daoDadosSul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        /**
         * Pegando os id's da activity_perfil, e setando nos atributos declarados la em cima
         */
        et_nome = findViewById(R.id.et_nome);
        et_email = findViewById(R.id.et_email);
        et_telefone = findViewById(R.id.et_telefone);
        et_senha = findViewById(R.id.et_senha);
        dao = new UsuarioDAO(this);
        //////////////////////////////////
        //INSERINDO A PASTAGEM COMO TESTE
          daoDadosSul = new DadosSulDAO(this);
          DadosSul p = new DadosSul("Andropogon", new double[]{4.0, 10.9, 15.0}, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
          DadosSul p2 = new DadosSul("Angola", new double[]{3.0, 9.7, 15.0}, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
          DadosSul p3 = new DadosSul("Aveia Branca", new double[]{2.0, 5.0, 8.0}, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
          daoDadosSul.inserirPastagem(p);
          daoDadosSul.inserirPastagem(p2);
          daoDadosSul.inserirPastagem(p3);
          Toast.makeText(this, "Pastagem inserida", Toast.LENGTH_SHORT).show();

        /*bt_voltar = findViewById(R.id.btVoltar);
        bt_voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                voltar();
            }
        });*/

        bt_criar = findViewById(R.id.bt_criar);
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

        bt_listar = findViewById(R.id.bt_listar);
        bt_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });

        bt_deletar = findViewById(R.id.bt_deletar);
        bt_deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarTodosUsuario();
            }
        });
    }


    /**
     * Este metodo esta declarado no onclik do botao Criar no activity_perfil, aqui dentro do metodo é criado um objeto usuario
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        long id = dao.inserirUsuario(u);
        Toast.makeText(this, "Usuario inserido com id " + id, Toast.LENGTH_SHORT).show();
        //O código abaixo da um tempo da 2 seg até voltar a outra página
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
     * Este metodo esta sendo chamado aqui, pra quando o usuario clicar em criar, os campos de editText esvaziem.
     */
    private void limparDados() {
        et_nome.setText("");
        et_email.setText("");
        et_telefone.setText("");
        et_senha.setText("");
    }
}
