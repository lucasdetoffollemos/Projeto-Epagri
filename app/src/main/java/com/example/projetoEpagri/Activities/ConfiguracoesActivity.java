package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.projetoEpagri.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        inicializa();

    }

    public void inicializa(){
        drawerLayout = findViewById(R.id.drawerLayout);
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
        finish();
    }

    public void clicarPerfil(View v){
        IndexActivity.redirecionaParaActivity(this, DadosPerfilActivity.class);
        finish();
    }

    public void clicarSobre(View v){
        IndexActivity.redirecionaParaActivity(this, SobreActivity.class);
        finish();
    }

    public void clicarConfig(View v){
        IndexActivity.fecharMenu(drawerLayout);
    }

    protected void  onPause() {
        IndexActivity.fecharMenu(drawerLayout);
        super.onPause();
    }

}