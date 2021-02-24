package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.Timer;
import java.util.TimerTask;

public class DadosPerfilActivity extends AppCompatActivity {
    private EditText et_nome, et_email, et_telefone, et_senha;
    private Button bt_cancelar, bt_atualizar, bt_excluir;
    private String nomeUsuario;
    private int idUsuario;
    private String nome, email, telefone, senha;

    //menu drawer
    private DrawerLayout drawerLayout;
    private View layout_incluido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_perfil);

        inicializa();
        setListeners();
    }

    public void inicializa(){
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");
        idUsuario = BancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(idUsuario);

        drawerLayout = findViewById(R.id.drawerLayout);
        et_nome = findViewById(R.id.et_nome);
        et_email = findViewById(R.id.et_email);
        et_telefone = findViewById(R.id.et_telefone);
        et_senha = findViewById(R.id.et_senha);

        et_nome.setText(usuario.getNome());
        et_email.setText(usuario.getEmail());
        et_telefone.setText(usuario.getTelefone());
        et_senha.setText(usuario.getSenha());

        bt_cancelar = findViewById(R.id.bt_cancelar);
        bt_atualizar = findViewById(R.id.bt_atualizar);
        bt_excluir = findViewById(R.id.bt_excluir);

        layout_incluido = findViewById(R.id.included_nav_drawer);

        TextView nome, email;
        nome = layout_incluido.findViewById(R.id.tv_nomeDinamico);
        email = layout_incluido.findViewById(R.id.tv_emailDinamico);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
    }

    public void setListeners(){
        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicarInicio(v);
            }
        });

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                atualizarDados(v);
            }
        });

        bt_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario();
            }
        });
    }

    private void atualizarDados(final View v) {
        //dados vindos dos edit text
        nome = et_nome.getText().toString();
        email = et_email.getText().toString();
        telefone = et_telefone.getText().toString();
        senha = et_senha.getText().toString();
        //dados vindos do banco
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(idUsuario);
        String nomeBanco = usuario.getNome();
        String emailBanco = usuario.getEmail();
        String telefoneBanco = usuario.getTelefone();
        String senhaBanco = usuario.getSenha();
        //verificacao, se os dados passados sao iguais aos do banco nao deixar atualizar
        if(nomeBanco.equals(nome) && emailBanco.equals(email) && telefoneBanco.equals(telefone) && senhaBanco.equals(senha)){
            Toast.makeText(DadosPerfilActivity.this, "Dados não modificados", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DadosPerfilActivity.this);

            builder.setTitle("ATENÇÃO");
            builder.setMessage( "Tem certeza que deseja continuar?" );
            builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nome = et_nome.getText().toString();
                    email = et_email.getText().toString();
                    telefone = et_telefone.getText().toString();
                    senha = et_senha.getText().toString();

                    BancoDeDados.usuarioDAO.updateUsuario(idUsuario, nome, email, telefone, senha);
                    Toast.makeText(DadosPerfilActivity.this, "Dados Atualizados com Sucesso!", Toast.LENGTH_SHORT).show();

                    nomeUsuario = nome;
                    clicarInicio(v);
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

    private void excluirUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DadosPerfilActivity.this);

        builder.setTitle("ATENÇÃO");
        builder.setMessage( "Tem certeza que deseja excluir sua conta?" );
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BancoDeDados.usuarioDAO.deleteUsuarioById(idUsuario);
                Toast.makeText(DadosPerfilActivity.this, "Usuário deletado", Toast.LENGTH_SHORT).show();
                //O código abaixo da um tempo da 1 seg até voltar a outra página.
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        IndexActivity.sairUsuarioExcluido(DadosPerfilActivity.this, MainActivity.class);
                    }
                }, 1000);
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

    //menu Drawer
    //Códido relacionados ao menu navigation Drawer
    public void clicarMenu(View v){
        IndexActivity.abrirMenu(drawerLayout);
    }

    @SuppressWarnings("unused")
    public void clicarLogo(View v){
        IndexActivity.fecharMenu(drawerLayout);
    }


    public void clicarInicio(View v){
        IndexActivity.redirecionaParaActivity(this, IndexActivity.class, nomeUsuario);
        finish();
    }

    public void clicarPerfil(View v){
        IndexActivity.fecharMenu(drawerLayout);
    }

    public void clicarSobre(View v){
        IndexActivity.redirecionaParaActivity(this, SobreActivity.class, nomeUsuario);
        finish();
    }

    public void clicarConfig(View v){
        IndexActivity.redirecionaParaActivity(this, ConfiguracoesActivity.class, nomeUsuario);
        finish();
    }

    public void clicarSair(View v){
        IndexActivity.sairApp(this, MainActivity.class);
    }

    protected void  onPause() {
        IndexActivity.fecharMenu(drawerLayout);
        super.onPause();
    }
}