package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.projetoEpagri.R;

public class SobreActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

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
        IndexActivity.fecharMenu(drawerLayout);

    }

    public void clicarConfig(View v){
        IndexActivity.redirecionaParaActivity(this, ConfiguracoesActivity.class);
        finish();
    }

    protected void  onPause() {
        IndexActivity.fecharMenu(drawerLayout);
        super.onPause();
    }


}
