package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Classes.Piquete;
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


        //Método que inseri os dados de pastagem.
        //inseriDadosTabelaPastagemSul();


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


    public void inseriDadosTabelaPastagemSul(){
        //INSERINDO A PASTAGEM COMO TESTE
        daoDadosSul = new DadosSulDAO(this);
        daoDadosSul.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);

        daoDadosSul.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
        daoDadosSul.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
        daoDadosSul.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100);
        daoDadosSul.inserirPastagem("Azevém", 2.0, 4.0, 9.0, new int[]{0, 0, 0, 5, 10, 20, 20, 20, 15, 10, 0, 0}, 100);
        daoDadosSul.inserirPastagem("B. Brizanta", 5.0, 11.3, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("B. Decumbes", 4.0, 12.8, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);

        daoDadosSul.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{25, 25, 10, 2, 0, 0, 0, 0, 0, 3, 15, 20}, 100);
        daoDadosSul.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100);
        daoDadosSul.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 20, 10, 5, 0, 0, 0, 0, 3, 7, 15, 20}, 100);
        daoDadosSul.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);

        daoDadosSul.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Tifhon 85", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        daoDadosSul.inserirPastagem("Triticale", 2.0, 4.0, 11.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
//          daoDadosSul.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100);//(Nao esta toda preenchida no excel)
        daoDadosSul.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100);
        daoDadosSul.inserirPastagem("Sudão", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100);
        daoDadosSul.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100);
        daoDadosSul.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);

//
        Toast.makeText(this, "Pastagem inserida", Toast.LENGTH_SHORT).show();
    }
}
