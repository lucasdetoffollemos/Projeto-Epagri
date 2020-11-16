package com.example.projetoEpagri.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetoEpagri.Fragments.FragmentDemandaAtual;
import com.example.projetoEpagri.Fragments.FragmentDemandaProposta;
import com.example.projetoEpagri.Fragments.FragmentOfertaAtual;
import com.example.projetoEpagri.Fragments.FragmentOfertaProposta;
import com.example.projetoEpagri.R;
import com.google.android.material.tabs.TabLayout;

public class TabsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private String nomePropriedade, nomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        inicializa();
        setListeners();
    }

    /**
     * Método responsável por inicializar os componentes da interface e os objetos da classe.
     */
    public void inicializa(){
        tabLayout = findViewById(R.id.tab_layout);

        Intent i = getIntent();
        nomePropriedade = i.getStringExtra("nomePropriedade");
        nomeUsuario = i.getStringExtra("nome_usuario");

        //Mostra o fragment da primeira aba, que é a selecionada quando a tela é carregada.
        fragment = FragmentOfertaAtual.newInstance(nomePropriedade);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_fragments, fragment);
        transaction.commit();
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela Tabs Activity.
     */
    public void setListeners(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int posicao = tabLayout.getSelectedTabPosition();

                switch (posicao){
                    case 0:
                        fragment = FragmentOfertaAtual.newInstance(nomePropriedade);
                        break;
                    case 1:
                        fragment = FragmentDemandaAtual.newInstance(nomePropriedade);
                        break;
                    case 2:
                        fragment = FragmentOfertaProposta.newInstance(nomePropriedade);
                        break;
                    case 3:
                        fragment = FragmentDemandaProposta.newInstance(nomePropriedade);
                        break;
                    default:
                        fragment = null;
                }

                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ll_fragments, fragment);
                //transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                enviaResposta();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Método utilizado para enviar de volta para a IndexActivity o nome do usuário caso ele clique no botão voltar.
     */
    public void enviaResposta(){
        Intent intent = new Intent();
        intent.putExtra("nome_usuario", nomeUsuario);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void clicarVoltarTabs(View view){
        finish();
    }
}