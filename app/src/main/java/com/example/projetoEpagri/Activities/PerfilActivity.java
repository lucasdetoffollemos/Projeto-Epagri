package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Classes.Usuario;

import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {
    private EditText et_nome, et_email, et_telefone, et_senha;
    public Button bt_criar;

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
        //Esta linha de código faz com que o teclado nao seja habilitado quando o usuário entra nesta activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        this.et_nome = findViewById(R.id.et_nome);
        this.et_email = findViewById(R.id.et_email);
        this.et_telefone = findViewById(R.id.et_telefone);
        this.et_senha = findViewById(R.id.et_senha);
        this.bt_criar = findViewById(R.id.bt_criar);
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
    }

    /**
     * Método responsável por criar um usuário e salvar no banco de dados.
     */
    public void criarPerfil(String nome, String email, String telefone, String senha){
        Usuario u = new Usuario(nome, email, telefone, senha);
        BancoDeDados.usuarioDAO.inserirUsuario(u);
        Toast.makeText(this, "Usuario criado com sucesso! ", Toast.LENGTH_SHORT).show();


        //O código abaixo da um tempo da 2 seg até voltar a outra página.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                String  nomeUsuario = et_nome.getText().toString();
                Intent i = new Intent(PerfilActivity.this, IndexActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("nome_usuario", nomeUsuario);
                startActivity(i);
            }
        }, 1000);
    }


    /**
     * Chamado no arquivo de layout
     */
    public void clicarVoltarPerfil(View view) {
        finish();
    }
}
