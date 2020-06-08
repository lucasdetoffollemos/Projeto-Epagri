package com.example.testetelas1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
//Ajustes para 30/05
//(FEITO)Fazer a validacao dos campos, por exemplo, nao deixar criar um usuario com os campos vazios.
//(FEITO)Esvaziar os editText quando o usuario clicar no botao Criar.
//(FEITO)Encaminhar o usuario para pagina de cadastro quando ele for inserido no banco de dados
//Ajustes para 01/05
//Nao deixar criar usuario igual.
//Fazer a autenticaçao no arquivo de Login(MainActivity), onde o usuario ira colocar seu nome e senha criados.




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

        /////
        botaoVoltar = findViewById(R.id.btVoltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                voltar();
            }
        });

        ////////
        botaoLista = findViewById(R.id.bt_lista);
        botaoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarUsuarios();
            }
        });
    }



    public void criarPerfil(View view){

        Usuario u = new Usuario(nome.getText().toString(), email.getText().toString(), telefone.getText().toString(), senha.getText().toString());
//        u.setNome(nome.getText().toString());
//        u.setEmail(email.getText().toString());
//        u.setTelefone(telefone.getText().toString());
//        u.setSenha(senha.getText().toString());
        long id = dao.inserirUsuario(u);
        Toast.makeText(this, "Usuario inserido com id " + id, Toast.LENGTH_SHORT).show();

        //limparDados();
    }



    /**
     * Este metodo esta sendo declarado no  onCreate, o método voltar tem o objetivo de voltar a página de login;
     */
    private void voltar() {
        Intent i = new Intent(Perfil.this, MainActivity.class);
        startActivity(i);
    }

    /**
     *Método criado para levar a página, onde são criados os usuários
     */
    private void listarUsuarios() {
        Intent i = new Intent(Perfil.this, ListaUsuarios.class);
        startActivity(i);
    }

    /**
     * Este metodo esta declarado no onclik do botao Criar no activity_perfil, aqui dentro do metodo é criado um objeto usuario
     * @param view
     */


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
