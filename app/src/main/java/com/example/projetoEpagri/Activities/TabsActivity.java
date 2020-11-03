package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        inicializa();
        setListeners();
    }

    public void inicializa(){
        tabLayout = findViewById(R.id.tab_layout);

        fragment = FragmentOfertaAtual.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_fragments, fragment);
        transaction.commit();
    }

    public void setListeners(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int posicao = tabLayout.getSelectedTabPosition();

                switch (posicao){
                    case 0:
                        fragment = FragmentOfertaAtual.newInstance();
                        break;
                    case 1:
                        fragment = FragmentDemandaAtual.newInstance();
                        break;
                    case 2:
                        fragment = FragmentOfertaProposta.newInstance();
                        break;
                    case 3:
                        fragment = FragmentDemandaProposta.newInstance();
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
}