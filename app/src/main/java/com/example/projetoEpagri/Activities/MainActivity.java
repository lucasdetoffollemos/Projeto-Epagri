package com.example.projetoEpagri.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoEpagri.R;
import com.example.projetoEpagri.Dao.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
    public TextView tv_criaPerfil;
    private EditText et_nome, et_senha;
    private Button bt_login;
    private UsuarioDAO daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nome = findViewById(R.id.et_nome);
        et_senha = findViewById(R.id.et_senha);
        daoUser = new UsuarioDAO(this);

        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tv_criaPerfil = findViewById(R.id.tv_criarPerfil);
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
        Intent i = new Intent(MainActivity.this, PerfilActivity.class);
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
             Intent i = new Intent(MainActivity.this, IndexActivity.class);
             i.putExtra("nome_usuario", nomeLog);
             startActivity(i);
         }
         else {
             Toast.makeText(getApplicationContext(), "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
         }
    }
}
