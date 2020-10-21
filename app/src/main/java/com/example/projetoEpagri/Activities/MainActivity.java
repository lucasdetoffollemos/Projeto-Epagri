package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;

/*
    COISAS A FAZER:
    - Implementar a não perda de layout ao voltar de Animais para Piquete.
    - Abrir e fechar conexões com o banco de dados no momento correto.
    - Implementar tratamento de exceção em todas as interações com o banco.
 */

public class MainActivity extends AppCompatActivity {
    private TextView tv_criaPerfil;
    private EditText et_nome, et_senha;
    private Button bt_login;
    public static BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializa();
        setListeners();

        if(bancoDeDados.verificaExistenciaTabela("dados_sul") &&
           bancoDeDados.verificaTabelaVazia("dados_sul")){
            inseriDadosTabelaPastagemSul();
        }
    }

    /**
     * Método responsável por inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        this.et_nome = findViewById(R.id.et_nome);
        this.et_senha = findViewById(R.id.et_senha);
        this.bt_login = findViewById(R.id.bt_login);
        this.tv_criaPerfil = findViewById(R.id.tv_criarPerfil);

        bancoDeDados = new BancoDeDados(this);
        bancoDeDados.abreConexao();
    }

    /**
     * Método responsável por setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListeners(){
        this.bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        this.tv_criaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPerfil();
            }
        });
    }

    /**
     * Método responsável por iniciar a Activity Perfil.
     */
    public void criarPerfil() {
        Intent i = new Intent(MainActivity.this, PerfilActivity.class);
        startActivity(i);
    }

    /**
     * Método resposável por realizar o login.
     */
    public void login() {
         String nome =  this.et_nome.getText().toString();
         String senha =  this.et_senha.getText().toString();
         Boolean checkEmailSenha =  bancoDeDados.usuarioDAO.login(nome, senha);

         if(checkEmailSenha){
             Toast.makeText(getApplicationContext(), "Usuário logado", Toast.LENGTH_SHORT).show();
             Intent i = new Intent(MainActivity.this, IndexActivity.class);
             i.putExtra("nome_usuario", nome);
             startActivity(i);
         }
         else {
             Toast.makeText(getApplicationContext(), "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
         }

         limparDados();
    }

    /**
     * Método responsável por limpar os dados do formulário de login.
     */
    private void limparDados(){
        this.et_nome.setText("");
        this.et_senha.setText("");
    }

    /**
     * Método responsável por inserir os dados na tabela dadosSul.
     */
    public void inseriDadosTabelaPastagemSul(){
        //INSERINDO A PASTAGEM COMO TESTE
        bancoDeDados.dadosSulDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Azevém", 2.0, 4.0, 9.0, new int[]{0, 0, 0, 5, 10, 20, 20, 20, 15, 10, 0, 0}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("B. Brizanta", 5.0, 11.3, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("B. Decumbes", 4.0, 12.8, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{25, 25, 10, 2, 0, 0, 0, 0, 0, 3, 15, 20}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 20, 10, 5, 0, 0, 0, 0, 3, 7, 15, 20}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Tifhon 85", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Triticale", 2.0, 4.0, 11.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100);
        //bancoDeDados.dadosSulDAO.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100);//(Nao esta toda preenchida no excel)
        bancoDeDados.dadosSulDAO.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Sudão", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100);
        bancoDeDados.dadosSulDAO.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);

        Toast.makeText(this, "Pastagem inserida", Toast.LENGTH_SHORT).show();
    }
}
