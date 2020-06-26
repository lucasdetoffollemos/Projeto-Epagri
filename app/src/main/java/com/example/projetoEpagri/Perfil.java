package com.example.projetoEpagri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Perfil extends AppCompatActivity {


    /**
     * Criando atributos para conectar com os id's do activity_perfil
     */
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText senha;
    private UsuarioDAO dao;
    //////////////////////
    public Button botaoVoltar;
    /////////////////////
    public Button botaoLista;
    ////////////////////
    private  DadosSulDAO daoDadosSul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        /**
         * Pegando os id's da activity_perfil, e setando nos atributos declarados la em cima
         */
        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        telefone = findViewById(R.id.editTelefone);
        senha = findViewById(R.id.editSenha);
        dao = new UsuarioDAO(this);
        //////////////////////////////////
        //INSERINDO A PASTAGEM COMO TESTE
//          daoDadosSul = new DadosSulDAO(this);
//          DadosSul p = new DadosSul("Andropogon", new double[]{4.0, 10.9, 15.0}, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
//          DadosSul p2 = new DadosSul("Angola", new double[]{3.0, 9.7, 15.0}, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
//          DadosSul p3 = new DadosSul("Aveia Branca", new double[]{2.0, 5.0, 8.0}, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
//          daoDadosSul.inserirPastagem(p);
//          daoDadosSul.inserirPastagem(p2);
//          daoDadosSul.inserirPastagem(p3);
//          Toast.makeText(this, "Pastagem inserida", Toast.LENGTH_SHORT).show();

        /////
//        botaoVoltar = findViewById(R.id.btVoltar);
//        botaoVoltar.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                voltar();
//            }
//        });




        ////////
        botaoLista = findViewById(R.id.bt_lista);
        botaoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });
    }


    /**
     * Este metodo esta declarado no onclik do botao Criar no activity_perfil, aqui dentro do metodo é criado um objeto usuario
     * @param view
     */
    public void criarPerfil(View view){
        Usuario u = new Usuario(nome.getText().toString(), email.getText().toString(), telefone.getText().toString(), senha.getText().toString());
        long id = dao.inserirUsuario(u);
        Toast.makeText(this, "Usuario inserido com id " + id, Toast.LENGTH_SHORT).show();
        //O código abaixo da um tempo da 2 seg até voltar a outra página
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
        //limparDados();
    }


    /**
     * Este metodo esta sendo declarado no  onCreate, o método voltar tem o objetivo de voltar a página de login;
     */
    private void voltar() {
//        Intent i = new Intent(Perfil.this, MainActivity.class);
//        startActivity(i);
        finish();
    }

    /**
     *Método criado para levar a página, onde são criados os usuários
     */
    private void listarUsuarios() {
        Intent i = new Intent(Perfil.this, ListaUsuarios.class);
        startActivity(i);
    }

    /**
     * Método responsavel por deletar todos os usuário da lista.
     * @param view
     */
    public void deletarTodosUsuario(View view){
        Usuario u = new Usuario(nome.getText().toString(), email.getText().toString(), telefone.getText().toString(), senha.getText().toString());
        dao.excluiTodosUsuarios(u);
        Toast.makeText(this, "Usuários deletados", Toast.LENGTH_SHORT).show();
        //limparDados();
    }

    /**
     * Este metodo esta sendo chamado aqui, pra quando o usuario clicar em criar, os campos de editText esvaziem.
     */
    private void limparDados() {
        nome.setText("");
        email.setText("");
        telefone.setText("");
        senha.setText("");
    }






}
