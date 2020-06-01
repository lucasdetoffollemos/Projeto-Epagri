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


    public Button botaoVoltar;
    //Criando atributos para conectar com os id's do activity_perfil
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText senha;
    private UsuarioDAO dao;

    //variavel de validaçao
    boolean dadosValidados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Pegando os id's da activity_perfil, e seta nos atributos declarados la em cima
        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        telefone = findViewById(R.id.editTelefone);
        senha = findViewById(R.id.editSenha);
        dao = new UsuarioDAO(this);
        botaoVoltar = findViewById(R.id.bt_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                voltar();
            }
        });
    }

    //Volta a pagina anterior
    private void voltar() {
        Intent i = new Intent(Perfil.this, MainActivity.class);
        startActivity(i);
    }

    //Este metodo esta declarado no onclik do botao Criar no activity_perfil, aqui dentro do metodo é criado um objeto usuario
    public void criar(View view){
        //Verificar se os dados estao digitados no formulario
        dadosValidados = validarFormulario();

        if(dadosValidados) {
            Usuario u = new Usuario();
            u.setNome(nome.getText().toString());
            u.setEmail(email.getText().toString());
            u.setTelefone(telefone.getText().toString());
            u.setSenha(senha.getText().toString());
            long id = dao.inserir(u);
            Toast.makeText(this, "Usuario inserido com id " + id, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Por favor preencha os campos acima.", Toast.LENGTH_SHORT).show();
        }

        //Este metodo esta sendo chamado aqui, pra quando o usuario clicar em criar, os campos de editText esvaziem.
        //svaiarDados();

    }

    //Este metodo esta sendo declarado la em cima no metodo criar
    private void esvaiarDados() {
        nome.setText("");
        email.setText("");
        telefone.setText("");
        senha.setText("");
    }


    //Este metodo faz a validaçao do formulario
    private boolean validarFormulario() {
        //Regra de validaçao, usando a classe TextUtils

        boolean retorno = false;

            if (!TextUtils.isEmpty(nome.getText().toString())) {
                retorno = true;
            } else {
                nome.setError("*");
                nome.requestFocus();
            }

            if (!TextUtils.isEmpty(email.getText().toString())) {
                retorno = true;
            } else {
                email.setError("*");
                email.requestFocus();
            }

            if (!TextUtils.isEmpty(telefone.getText().toString())) {
                retorno = true;
            } else {
                telefone.setError("*");
                telefone.requestFocus();
            }

            if (!TextUtils.isEmpty(senha.getText().toString())) {
                retorno = true;
            } else {
                senha.setError("*");
                senha.requestFocus();
            }

        return retorno;
    }

}
