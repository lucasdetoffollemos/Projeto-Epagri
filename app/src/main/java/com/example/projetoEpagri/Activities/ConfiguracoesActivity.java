package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.R;

public class ConfiguracoesActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Button bt_edit_cfa, bt_edit_cfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        inicializa();
        setListeners();
    }

    public void inicializa(){
        drawerLayout = findViewById(R.id.drawerLayout);
        bt_edit_cfa = findViewById(R.id.bt_edit_cfa);
        bt_edit_cfb = findViewById(R.id.bt_edit_cfb);
    }

    public void setListeners(){
        bt_edit_cfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracoesActivity.this, EditarDadosActivity.class);
                i.putExtra("dados", IDadosSchema.TABELA_DADOS_NORTE);
                startActivity(i);
            }
        });

        bt_edit_cfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracoesActivity.this, EditarDadosActivity.class);
                i.putExtra("dados", IDadosSchema.TABELA_DADOS_SUL);
                startActivity(i);
            }
        });
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

    public void clicarSair(View v){
        IndexActivity.sairApp(this);
    }

    protected void  onPause() {
        IndexActivity.fecharMenu(drawerLayout);
        super.onPause();
    }



}