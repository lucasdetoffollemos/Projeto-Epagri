package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.ListViewPropriedadesAdapter;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private TextView tv_bemVindo;
    private ArrayList<Propriedade> listaPropriedade;
    private ListViewPropriedadesAdapter listViewPropriedadesAdapter;
    private Button bt_cadastrarPropriedade;
    private String nomeUsuario;
    private final int codigoRequisicao = 1; //Código para identificar a activity no método onActivityResult.
    private int usuarioId;

    //Menu Drawer
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        inicializa();
        setListeners();
    }

    /**
     * Método utilizado para inicializar os componentes da interface e os objetos da classe.
     */
    @SuppressLint("SetTextI18n")
    public void inicializa(){
        //Recebe a mensagem (nome do usuário) da activity anterior (LoginActivity).
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        tv_bemVindo = findViewById(R.id.tv_tituloIndex);
        String msgBoasVindas = getString(R.string.txt_tv_bemVindo) + " " + nomeUsuario + "!";
        tv_bemVindo.setText(msgBoasVindas);

        ListView lv_propriedades = findViewById(R.id.lv_propriedades);
        bt_cadastrarPropriedade = findViewById(R.id.bt_levaPropriedade);

        //Recupera o ID do usuário baseado no nome recebido da activity Main.
        usuarioId = BancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
        //Recupera as propriedades do usuário baseado no seu ID.
        listaPropriedade = BancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(usuarioId);

        //Se existirem propriedades, esconde o textview "Bem vindo Usuário"
        if(listaPropriedade.size() > 0){
            tv_bemVindo.setVisibility(View.INVISIBLE);
        }

        listViewPropriedadesAdapter = new ListViewPropriedadesAdapter(this, listaPropriedade, nomeUsuario);
        lv_propriedades.setAdapter(listViewPropriedadesAdapter);

        //Drawer menu
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    /**
     * Método utilizado para setar os listener dos botões e tudo mais que for clicável na tela login.
     */
    public void setListeners(){
        bt_cadastrarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaActivityPropiedade(nomeUsuario);
            }
        });
    }

    /**
     * Método responsável por iniciar a PropriedadeActivity.
     * O nome precisa ser enviado junto, caso contrário, quando o usuário retorna, ele é perdido.
     */
    public void vaiParaActivityPropiedade(String nomeUsuario) {
        Intent i = new Intent(IndexActivity.this, PropriedadeActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        startActivityForResult(i, this.codigoRequisicao);
    }

    public void atualizaListView(){
        //Consulta o banco e atualiza o listview.
        listaPropriedade = BancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(usuarioId);
        listViewPropriedadesAdapter.getData().clear();
        listViewPropriedadesAdapter.getData().addAll(listaPropriedade);
        listViewPropriedadesAdapter.notifyDataSetChanged();

        if(listaPropriedade.size() > 0){
            tv_bemVindo.setVisibility(View.INVISIBLE);
        }
        else{
            tv_bemVindo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Método responsável por lidar com as respostas enviadas da activity Propriedade.
     * @param requestCode Representa o código da activity que fez a requisição.
     * @param resultCode Representa o código do resultado enviado.
     * @param data Representa a informação enviada como resposta.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.codigoRequisicao) {
            if(resultCode == Activity.RESULT_OK){
                nomeUsuario = data.getStringExtra("nome_usuario");
                String msgBoasVindas = R.string.txt_tv_bemVindo + nomeUsuario;
                tv_bemVindo.setText(msgBoasVindas);

                atualizaListView();
            }
        }
    }

    /**
     * Método que tem como objetivo, ver se o usuário quer sair mesmo, chamada quando clicado no botão de voltar do celular.
     */
    public static void sairApp(final Activity a){
        //Criando a caixa de pergunta, se o usuário quer ou não sair do app
        AlertDialog.Builder builder = new AlertDialog.Builder(a);

        builder.setTitle("Sair");
        builder.setMessage( "Tem certeza que deseja deslogar? " );
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                a.finish();
            }
        });

        builder.setNegativeButton(" NÃO ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();}

    public  void onBackPressed(){
        sairApp(this);
    }

    //Códido relacionados ao menu navigation Drawer
    public void clicarMenu(View v){
        abrirMenu(drawerLayout);
    }

    public static void abrirMenu(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @SuppressWarnings("unused")
    public void clicarLogo(View v){
        fecharMenu(drawerLayout);
    }

    public static void fecharMenu(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clicarInicio(View v){
        fecharMenu(drawerLayout);
    }

    public void clicarPerfil(View v){
        redirecionaParaActivity(this, DadosPerfilActivity.class);
    }

    public void clicarSobre(View v){
        redirecionaParaActivity(this, SobreActivity.class);
    }

    public void clicarConfig(View v){
        redirecionaParaActivity(this, ConfiguracoesActivity.class);
    }

    public void clicarSair(View v){
        sairApp(this);
    }

    public static void redirecionaParaActivity(Activity essa, Class aquela){
        Intent i = new Intent(essa, aquela);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        essa.startActivity(i);
    }


    protected void  onPause() {
        fecharMenu(drawerLayout);
        super.onPause();
    }

}