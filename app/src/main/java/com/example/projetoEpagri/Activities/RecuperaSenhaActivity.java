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

public class RecuperaSenhaActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1 ;
    private EditText et_nome, et_email;
    private Button bt_modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);
        inicializa();
        setListeners();
    }

    private void inicializa() {
        //Esta linha de código faz com que o teclado nao seja habilitado quando o usuário entra nesta activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et_nome = findViewById(R.id.et_nome);
        et_email = findViewById(R.id.et_email);
        bt_modifica = findViewById(R.id.bt_modificar);
    }

    private void setListeners() {
        bt_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verificaNomeEmail();
            }
        });
    }

    //Chamado no arquivo layout
    public void clicarVoltarRecuperaSenha(View v){
        finish();
    }

    //Verifica se o nome e o usuário existem, se existir leva para outra página com o nome, se não existir apenas apresenta um toast
    private void verificaNomeEmail() {
        String nome = et_nome.getText().toString();
        String email = et_email.getText().toString();
        Boolean usuarioCheck = BancoDeDados.usuarioDAO.verificaNomeEmailUsuario(nome, email);
        if(usuarioCheck){
            vaiParaActivityModificaSenha(nome);
        }
        else{
            Toast.makeText(RecuperaSenhaActivity.this, "Usuário inexistente", Toast.LENGTH_SHORT).show();
        }
    }

    private void vaiParaActivityModificaSenha(String nome) {
        Intent i = new Intent(RecuperaSenhaActivity.this, ModificaSenhaActivity.class);
        i.putExtra("nome_usuario", nome);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            finish();
        }
    }
}