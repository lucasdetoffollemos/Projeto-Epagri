package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
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
    public BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializa();
        setListeners();

        if(bancoDeDados.verificaExistenciaTabela("dados_norte") && bancoDeDados.verificaTabelaVazia("dados_norte")){
            inseriDadosTabelaPastagemNorte();
        }

        if(bancoDeDados.verificaExistenciaTabela("dados_sul") && bancoDeDados.verificaTabelaVazia("dados_sul")){
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
                vaiParaActivityPerfil();
            }
        });
    }

    /**
     * Método resposável por realizar o login.
     */
    public void login() {
        String nome =  this.et_nome.getText().toString();
        String senha =  this.et_senha.getText().toString();
        boolean checkEmailSenha =  BancoDeDados.usuarioDAO.login(nome, senha);

        if(checkEmailSenha){
            Toast.makeText(getApplicationContext(), "Usuário logado", Toast.LENGTH_SHORT).show();
            vaiParaActivityIndex(nome);
        }
        else {
            Toast.makeText(getApplicationContext(), "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
        }

        limparDados();
    }

    /**
     * Método responsável por iniciar a Activity Perfil.
     */
    public void vaiParaActivityPerfil() {
        Intent i = new Intent(MainActivity.this, PerfilActivity.class);
        startActivity(i);
    }

    /**
     * Método responsável por iniciar a Activity Index.
     * @param nomeUsuario Representa o nome do usuário logado.
     */
    public void vaiParaActivityIndex(String nomeUsuario){
        Intent i = new Intent(MainActivity.this, IndexActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        startActivity(i);
    }

    /**
     * Método responsável por limpar os dados do formulário de login.
     */
    private void limparDados(){
        this.et_nome.setText("");
        this.et_senha.setText("");
    }

    /**
     * Método responsável por inserir os dados das pastagem do cfa (norte) na tabela.
     */
    public void inseriDadosTabelaPastagemNorte(){
        //Dados Norte - cfa
        BancoDeDados.dadosDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Azevém", 2.0, 4.0, 7.0, new int[]{0, 0, 0, 0, 3, 22, 30, 30, 10, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("B. Brizanta", 5.0, 12, 23.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("B. Decumbens", 4.0, 12.8, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{20, 15, 15, 15, 5, 2, 1, 2, 3, 5, 7, 10}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Estrela Africana", 4.0, 11.8, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{25, 15, 10, 0, 0, 0, 0, 0, 0, 0, 20, 30}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Tifthon 85", 6.0, 15.0, 25.0, new int[]{17, 13, 7, 6, 4, 3, 2, 4, 8, 9, 13, 14}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Triticale", 2.0, 4.0, 12.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        //bancoDeDados.dadosDAO.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);//(Nao esta toda preenchida no excel)
        //bancoDeDados.dadosDAO.inserirPastagem("Alto Grao", 0.0, 0.0, 25.0, new int[]{0, 0, 34, 33, 33, 0, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);//(Nao esta toda preenchida no excel)
        BancoDeDados.dadosDAO.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        BancoDeDados.dadosDAO.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_NORTE);
    }

    /**
     * Método responsável por inserir os dados das pastagem do cfb (sul) na tabela.
     */
    public void inseriDadosTabelaPastagemSul(){
        //Dados Sul - cfb
        BancoDeDados.dadosDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Azevém", 2.0, 4.0, 9.0, new int[]{0, 0, 0, 5, 10, 20, 20, 20, 15, 10, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("B. Brizanta", 5.0, 11.3, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("B. Decumbens", 4.0, 12.8, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{25, 25, 10, 2, 0, 0, 0, 0, 0, 3, 15, 20}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Estrela Africana", 4.0, 11.8, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 20, 10, 5, 0, 0, 0, 0, 3, 7, 15, 20}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Tifthon 85", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Triticale", 2.0, 4.0, 11.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        //bancoDeDados.dadosDAO.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);//(Nao esta toda preenchida no excel)
        BancoDeDados.dadosDAO.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Sudão", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        BancoDeDados.dadosDAO.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
    }
}
