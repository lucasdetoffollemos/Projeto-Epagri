package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.projetoEpagri.Fragments.ConfiguracoesFragment;
import com.example.projetoEpagri.Fragments.EditarPerfilFragment;
import com.example.projetoEpagri.Fragments.IndexFragment;
import com.example.projetoEpagri.Fragments.SobreFragment;
import com.example.projetoEpagri.R;

import java.util.List;

public class IndexActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{
    public static String nome_usuario;
    //private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //fragmentManager = getSupportFragmentManager();
        //fragmentManager.addOnBackStackChangedListener(this);

        Intent intent = getIntent();
        nome_usuario = intent.getStringExtra("nome_usuario");

        //Mostra o IndexFragment.
        Fragment index_fragment = IndexFragment.newInstance();
        MainActivity.startFragment(index_fragment, "index_fragment", R.id.ll_index, false, false, this);
    }

    /**
     * Método responsável por retornar o fragment no topo da pilha.
     * @return
     */
    public Fragment getTopFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        //Toast.makeText(this, topFragment.getTag(), Toast.LENGTH_LONG).show();
        return list.get(list.size() - 1);
    }

    @Override
    public void onBackStackChanged() {
        //Fragment fragment = getTopFragment();
        //if(fragment.getTag().equals("piquete_fragment")){
            //Toast.makeText(this, "Achei o piquete", Toast.LENGTH_SHORT).show();

       // }
    }

    /**
     * Método chamado ao clicar no botão voltar do smartphone.
     */
    public void onBackPressed(){
        if(getTopFragment().getTag().equals("index_fragment")){
            logout();
        }
        else{
            super.onBackPressed();
        }
    }

    /**
     * Método responsável por mostrar o menu.
     * @param drawerLayout Local onde o menu é renderizado.
     */
    public static void abrirMenu(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Método responsável por fechar o menu.
     * @param drawerLayout Local onde o menu é renderizado.
     */
    public static void fecharMenu(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Método executado ao clicar no botão "Perfil" do menu.
     * @param v View.
     */
    public void clickMenuItemPerfil(View v) {
        if(!getTopFragment().getTag().equals("editar_perfil_fragment")){
            Fragment editar_perfil_fragment = EditarPerfilFragment.newInstance();
            MainActivity.startFragment(editar_perfil_fragment, "editar_perfil_fragment", R.id.ll_index, true, true, this);
        }
    }

    /**
     * Método executado ao clicar no botão "Sobre" do menu.
     * @param v View.
     */
    public void clickMenuItemSobre(View v) {
        if(!getTopFragment().getTag().equals("sobre_fragment")){
            Fragment sobre_fragment = SobreFragment.newInstance();
            MainActivity.startFragment(sobre_fragment, "sobre_fragment", R.id.ll_index, true, true, this);
        }
    }

    /**
     * Método executado ao clicar no botão "Configurações" do menu.
     * @param v View.
     */
    public void clickMenuItemConfiguracoes(View v) {
        if(!getTopFragment().getTag().equals("configuracoes_fragment")){

            //Criando a caixa de pergunta, se o usuário quer ou não sair do app
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Configurações");
            builder.setMessage("Quais dados você deseja editar?");
            builder.setPositiveButton("cfa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Fragment configuracoes_fragment = ConfiguracoesFragment.newInstance("cfa");
                    MainActivity.startFragment(configuracoes_fragment, "configuracoes_fragment", R.id.ll_index, true, true, IndexActivity.this);
                }
            });

            builder.setNegativeButton("cfb", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Fragment configuracoes_fragment = ConfiguracoesFragment.newInstance("cfb");
                    MainActivity.startFragment(configuracoes_fragment, "configuracoes_fragment", R.id.ll_index, true, true, IndexActivity.this);

                }
            });

            builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            builder.show();
        }
    }

    /**
     * Método executado ao clicar no botão "Sair" do menu.
     * @param v View.
     */
    public void clickMenuItemSair(View v) {
        logout();
    }

    /**
     * Método responsável por mostrar a janela de confirmação antes do usuário sair.
     */
    public void logout(){
        //Criando a caixa de pergunta, se o usuário quer ou não sair do app
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sair");
        builder.setMessage("Tem certeza que deseja sair?");
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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