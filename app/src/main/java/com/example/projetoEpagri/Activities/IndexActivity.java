package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.Fragments.ConfiguracoesFragment;
import com.example.projetoEpagri.Fragments.EditarPerfilFragment;
import com.example.projetoEpagri.Fragments.IndexFragment;
import com.example.projetoEpagri.Fragments.SobreFragment;
import com.example.projetoEpagri.R;

import java.util.List;

public class IndexActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{
    //public static String nome_usuario, senha;
    public static Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Intent intent = getIntent();
        String nome_usuario = intent.getStringExtra("nome_usuario");
        String senha = intent.getStringExtra("senha");

        usuario = MainActivity.bancoDeDados.usuarioDAO.getUsuario(nome_usuario);

        if(usuario != null){
            int id_usuario = MainActivity.bancoDeDados.usuarioDAO.getUSuarioId(nome_usuario);
            usuario.setId(id_usuario);
            usuario.setSenha(senha);
        }

        //Mostra o IndexFragment.
        Fragment index_fragment = IndexFragment.newInstance();
        MainActivity.startFragment(index_fragment, "index_fragment", R.id.ll_index, false, false, this);
    }

    /**
     * Método responsável por retornar o fragment no topo da pilha.
     * @return Retorna o fragment no topo do stack.
     */
    public Fragment getTopFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        return list.get(list.size() - 1);
    }

    //Pode ser útil.
    /**
     * Método executado toda vez que a pilha de fragments sofre alteração.
     */
    @Override
    public void onBackStackChanged() {}

    /**
     * Método chamado ao clicar no botão voltar do smartphone.
     */
    public void onBackPressed(){
        String tag = getTopFragment().getTag();

        if(tag != null && tag.equals("index_fragment")){
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
        String tag = getTopFragment().getTag();

        if(tag != null && !tag.equals("editar_perfil_fragment")){
            Fragment editar_perfil_fragment = EditarPerfilFragment.newInstance();
            MainActivity.startFragment(editar_perfil_fragment, "editar_perfil_fragment", R.id.ll_index, true, true, this);
        }
    }

    /**
     * Método executado ao clicar no botão "Sobre" do menu.
     * @param v View.
     */
    public void clickMenuItemSobre(View v) {
        String tag = getTopFragment().getTag();

        if(tag != null && !tag.equals("sobre_fragment")){
            Fragment sobre_fragment = SobreFragment.newInstance();
            MainActivity.startFragment(sobre_fragment, "sobre_fragment", R.id.ll_index, true, true, this);
        }
    }

    /**
     * Método executado ao clicar no botão "Configurações" do menu.
     * @param v View.
     */
    public void clickMenuItemConfiguracoes(View v) {
        String tag = getTopFragment().getTag();

        if(tag != null && !tag.equals("configuracoes_fragment")){
            if(usuario != null && usuario.getTipo_perfil().equals("Técnico")){
                //Criando a caixa de pergunta, se o usuário quer ou não sair do app
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Configurações");
                builder.setMessage("Quais dados você deseja editar?");
                builder.setPositiveButton("cfa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportFragmentManager().popBackStack();

                        Fragment configuracoes_fragment = ConfiguracoesFragment.newInstance("cfa");
                        MainActivity.startFragment(configuracoes_fragment, "configuracoes_fragment", R.id.ll_index, true, true, IndexActivity.this);
                    }
                });

                builder.setNegativeButton("cfb", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportFragmentManager().popBackStack();

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
            else{
                Toast.makeText(this, "Operação Inválida! Somente o perfil de Técnico tem acesso a essa funcionalidade!", Toast.LENGTH_LONG).show();
            }
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
                voltaParaLogin();
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

    //método que é chamado quando usuário clica na opção sair, e é redirecionado para a activity main/login, levando um codigo com ela, e removendo toda a pilha de activities anteriores
    public void voltaParaLogin(){
        Intent i = new Intent(IndexActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("cod", 1);
        startActivity(i);
        //Destroi a IndexActivity.
        finish();
    }
}