package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

public class RecuperaSenhaActivity extends AppCompatActivity {
    private EditText et_nome, et_telefone;
    private Button bt_modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);
        inicializa();
        setListeners();
    }

    private void inicializa() {
        et_nome = findViewById(R.id.et_nome);
        et_telefone = findViewById(R.id.et_telefone);
        bt_modifica = findViewById(R.id.bt_modificar);
    }

    private void setListeners() {
        bt_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verificaNomeETelefone();
            }
        });
    }

    //Chamado no arquivo layout
    public void clicarVoltarRecuperaSenha(View v){
        finish();
    }

    private void verificaNomeETelefone() {
        String nome = et_nome.getText().toString();
        String telefone = et_telefone.getText().toString();
        Boolean usuarioCheck = BancoDeDados.usuarioDAO.verificaNomeTelefoneUusario(nome, telefone);
        int idUsuario = BancoDeDados.usuarioDAO.getUSuarioId(nome);
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(idUsuario);
        String senha = usuario.getSenha();
        if(usuarioCheck){
            Toast.makeText(RecuperaSenhaActivity.this, "Existe" + senha, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(RecuperaSenhaActivity.this, "Nao existe", Toast.LENGTH_SHORT).show();
        }
    }
}