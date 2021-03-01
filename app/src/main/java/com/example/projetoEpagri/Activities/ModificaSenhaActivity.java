package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.Timer;
import java.util.TimerTask;

public class ModificaSenhaActivity extends AppCompatActivity {
    private Integer idUsuario;
    private String nomeUsuario, senhaEt;
    private EditText et_senha;
    private Button bt_alterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_senha);
        inicializa();
        setListeners();

    }
    private void inicializa() {
        //Esta linha de código faz com que o teclado nao seja habilitado quando o usuário entra nesta activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        this.nomeUsuario = intent.getStringExtra("nome_usuario");
        this.idUsuario = BancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
        et_senha = findViewById(R.id.et_senha);
        bt_alterar = findViewById(R.id.bt_alterar);
    }

    private void setListeners() {
        bt_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               mudaSenha();
            }
        });
    }

    private void mudaSenha() {
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(idUsuario);
        String senhaBanco = usuario.getSenha();
        senhaEt = et_senha.getText().toString();
        //se a senha ja existe mandar um toas, senao, alterar a senha.
        if(senhaBanco.equals(senhaEt)) {
            Toast.makeText(ModificaSenhaActivity.this, "Senha já existente", Toast.LENGTH_SHORT).show();
        }else{
            //Criando a caixa de pergunta, se o usuário quer ou não alterar sua senha
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Alterar senha");
            builder.setMessage( "Tem certeza que deseja alterar sua senha?" );
            builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BancoDeDados.usuarioDAO.updateUsuarioId(idUsuario, senhaEt);
                    Toast.makeText(ModificaSenhaActivity.this, "Senha alterada", Toast.LENGTH_SHORT).show();

                    //O código abaixo da um tempo da 2 seg até voltar a outra página.
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent data = new Intent();
                            setResult(RESULT_OK, data);
                            finish();
                        }
                    }, 1500);

                }
            });

            builder.setNegativeButton(" NÃO ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }
    }

    //chamado no arquivo de layout
    public void clicarVoltarModificaSenha(View v){
        finish();
    }
}