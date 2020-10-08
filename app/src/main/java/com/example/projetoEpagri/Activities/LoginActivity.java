package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Dao.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {
    public TextView tv_criaPerfil;
    private EditText et_nome, et_senha;
    private Button bt_login;
    private UsuarioDAO daoUser;
    private DadosSulDAO daoDadosSul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializa();
        setListeners();

        //Método que insere os dados de pastagem.
        if(daoDadosSul.getBd().verificaTabelaVazia("dadosSul")){
            Log.i("verifica", "tabela Vazia!");
            inseriDadosTabelaPastagemSul();
        }
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        et_nome = findViewById(R.id.et_nome);
        et_senha = findViewById(R.id.et_senha);
        bt_login = findViewById(R.id.bt_login);
        tv_criaPerfil = findViewById(R.id.tv_criarPerfil);

        daoUser = new UsuarioDAO(this);
        daoDadosSul = new DadosSulDAO(this);
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListeners(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        tv_criaPerfil.setOnClickListener(new View.OnClickListener() {
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
        Intent i = new Intent(LoginActivity.this, PerfilActivity.class);
        startActivity(i);
    }

    /**
     *Se o email e senha forem iguais ao cadastrado pelo usuário no banco de dados, o login levará para pagina de bem vindo.
     */
    public void login() {
         String nomeLog = et_nome.getText().toString();
         String senhaLog = et_senha.getText().toString();
         Boolean checkEmailSenha = daoUser.logar(nomeLog, senhaLog);

         if(checkEmailSenha){
             Toast.makeText(getApplicationContext(), "Usuário logado", Toast.LENGTH_SHORT).show();
             Intent i = new Intent(LoginActivity.this, IndexActivity.class);
             i.putExtra("nome_usuario", nomeLog);
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
        et_nome.setText("");
        et_senha.setText("");
    }

    /**
     * Método responsável por inserir os dados na tabela dadosSul.
     */
    public void inseriDadosTabelaPastagemSul(){
        //INSERINDO A PASTAGEM COMO TESTE
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
//      daoDadosSul.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100);//(Nao esta toda preenchida no excel)
        daoDadosSul.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100);
        daoDadosSul.inserirPastagem("Sudão", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100);
        daoDadosSul.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100);
        daoDadosSul.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100);

        Toast.makeText(this, "Pastagem inserida", Toast.LENGTH_SHORT).show();
    }
}
