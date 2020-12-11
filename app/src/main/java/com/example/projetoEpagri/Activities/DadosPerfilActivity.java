package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.projetoEpagri.R;

public class DadosPerfilActivity extends AppCompatActivity {

    //menu drawer
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_perfil);
        inicializa();
    }

    public void inicializa(){
        drawerLayout = findViewById(R.id.drawerLayout);
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
        finish();
    }

    public void clicarPerfil(View v){
        IndexActivity.fecharMenu(drawerLayout);
    }

    public void clicarSobre(View v){
        IndexActivity.redirecionaParaActivity(this, SobreActivity.class);
        finish();
    }

    public void clicarConfig(View v){
        IndexActivity.redirecionaParaActivity(this, ConfiguracoesActivity.class);
        finish();
    }

    public void clicarSair(View v){
        IndexActivity.sairApp(this);
    }

    protected void  onPause() {
        IndexActivity.fecharMenu(drawerLayout);
        super.onPause();
    }



}