package com.example.projetoEpagri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public TextView criaPerfil;
    private EditText nome;
    private EditText senha;
    private UsuarioDAO daoUser;
    public static  String mensagem_extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nome = findViewById(R.id.et_nome);
        senha = findViewById(R.id.et_senha);
        daoUser = new UsuarioDAO(this);

        criaPerfil = findViewById(R.id.tv_criaPerfil);
        //textView.setMovementMethod(LinkMovementMethod.getInstance());

        criaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enviaParaPerfil();
            }
        });
    }


    public void enviaParaPerfil() {
        Intent i = new Intent(MainActivity.this, Perfil.class);
        startActivity(i);
    }


    /**
     *Se o email e senha forem iguais ao cadastrado pelo usuário no banco de dados, o login levará para pagina de bem vindo.
     * @param view
     */
    public void logar(View view) {

         String nomeLog = nome.getText().toString();
         String senhaLog = senha.getText().toString();
         Boolean checkEmailSenha = daoUser.logar(nomeLog, senhaLog);
         if(checkEmailSenha){
             Toast.makeText(getApplicationContext(), "Usuário logado", Toast.LENGTH_SHORT).show();
             Intent i = new Intent(MainActivity.this, Index.class);
             String nomeBemVindo = nome.getText().toString();
             i.putExtra(mensagem_extra, nomeBemVindo);
             startActivity(i);
             finish();
         }
         else {
             Toast.makeText(getApplicationContext(), "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
         }
    }
}
