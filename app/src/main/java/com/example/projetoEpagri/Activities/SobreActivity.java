package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

public class SobreActivity extends AppCompatActivity {
    private String nomeUsuario;
    private int idUsuario;

    private DrawerLayout drawerLayout;
    private View layout_incluido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        inicializa();
    }

    public void inicializa(){
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");
        idUsuario = BancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(idUsuario);

        drawerLayout = findViewById(R.id.drawerLayout);
        layout_incluido = findViewById(R.id.included_nav_drawer);

        TextView nome, email;
        nome = layout_incluido.findViewById(R.id.tv_nomeDinamico);
        email = layout_incluido.findViewById(R.id.tv_emailDinamico);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
    }

    //Menu drawer
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
        IndexActivity.redirecionaParaActivity(this, DadosPerfilActivity.class, nomeUsuario);
        finish();
    }

    public void clicarSobre(View v){
        IndexActivity.fecharMenu(drawerLayout);

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
