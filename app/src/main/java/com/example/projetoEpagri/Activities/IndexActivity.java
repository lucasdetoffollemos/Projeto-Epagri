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
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.ListViewPropriedadesAdapter;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private TextView tv_bemVindo;
    private ArrayList<Propriedade> listaPropriedade;
    private ListView lv_propriedades;
    private ListViewPropriedadesAdapter listViewPropriedadesAdapter;
    private Button bt_cadastrarPropriedade;
    private String nomeUsuario;
    private int usuarioId;
    private static final int REQUEST_CODE = 1;//Código para identificar a activity no método onActivityResult().

    //Menu Drawer
    private DrawerLayout drawerLayout;
    private View layout_incluido;

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

        lv_propriedades = findViewById(R.id.lv_propriedades);
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
        registerForContextMenu(lv_propriedades);

        //Drawer menu
        drawerLayout = findViewById(R.id.drawerLayout);
        layout_incluido = findViewById(R.id.included_nav_drawer);

        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(usuarioId);

        TextView nome, email;
        nome = layout_incluido.findViewById(R.id.tv_nomeDinamico);
        email = layout_incluido.findViewById(R.id.tv_emailDinamico);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
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

        lv_propriedades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TO-DO.
            }
        });
    }

    /**
     * Método responsável por criar o menu ao clicar em uma propriedade da lista.
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_propriedade, menu);
        menu.setHeaderTitle("Selecione uma opção");
    }

    /**
     * Método responsável pelo clique em uma das opções do menu.
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //Recebe as informações para qual item da lista o menu está sendo exibido.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Propriedade propriedade = (Propriedade) listViewPropriedadesAdapter.getItem(info.position);
        int idPropriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());

        if(item.getItemId() == R.id.mi_ver_dados){
            Intent i = new Intent(IndexActivity.this, TabsActivity.class);
            i.putExtra("nomePropriedade", propriedade.getNome());
            i.putExtra("nome_usuario", nomeUsuario);
            IndexActivity.this.startActivity(i);
        }
        else if(item.getItemId() == R.id.mi_fazer_proposta) {
            Toast.makeText(getApplicationContext(), "fazer proposta", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.mi_ver_grafico_atual){
            ArrayList<Double> totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_ATUAL);
            ArrayList<Double> totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_ATUAL);

            Intent i = new Intent(IndexActivity.this, GraficoActivity.class);
            i.putExtra("totaisPiqueteMes", totaisPiqueteMes);
            i.putExtra("totaisAnimalMes", totaisAnimalMes);
            IndexActivity.this.startActivity(i);
        }
        else if(item.getItemId() == R.id.mi_ver_grafico_proposta){
            ArrayList<Double> totaisPiqueteMes = BancoDeDados.totalPiqueteMesDAO.getTotalMesByPropId(idPropriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA);
            ArrayList<Double> totaisAnimalMes = BancoDeDados.totalAnimaisDAO.getTotalMesByPropId(idPropriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA);

            if(!totaisPiqueteMes.isEmpty() && !totaisAnimalMes.isEmpty()){
                Intent i = new Intent(IndexActivity.this, GraficoActivity.class);
                i.putExtra("totaisPiqueteMes", totaisPiqueteMes);
                i.putExtra("totaisAnimalMes", totaisAnimalMes);
                IndexActivity.this.startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(), "Faça a proposta para poder visualizar o gráfico!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            return false;
        }
        return true;
    }

    /**
     * Método responsável por iniciar a PropriedadeActivity.
     * O nome precisa ser enviado junto, caso contrário, quando o usuário retorna, ele é perdido.
     */
    public void vaiParaActivityPropiedade(String nomeUsuario) {
        Intent i = new Intent(IndexActivity.this, PropriedadeActivity.class);
        i.putExtra("nome_usuario", nomeUsuario);
        startActivityForResult(i, REQUEST_CODE);
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
     * Método que tem como objetivo, ver se o usuário quer sair mesmo, chamada quando clicado no botão de voltar do celular.
     */
    public static void sairApp(final Activity a, final Class clas){
        //Criando a caixa de pergunta, se o usuário quer ou não sair do app
        AlertDialog.Builder builder = new AlertDialog.Builder(a);

        builder.setTitle("Sair");
        builder.setMessage( "Tem certeza que deseja sair?" );
        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(a, clas);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                a.startActivity(i);
                a.finish();
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

    /**
     * Funçao chamada na activity dadosperfil, que tem como responsabilidade quando o usuario excluir sua conta, direciona-lo para main activity
     * @param a
     * @param clas
     */
    public static void sairUsuarioExcluido(final Activity a, final Class clas){
                Intent i = new Intent(a, clas);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                a.startActivity(i);
    }

    public  void onBackPressed(){
        sairApp(this, MainActivity.class);
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
        redirecionaParaActivity(this, DadosPerfilActivity.class, this.nomeUsuario);
    }

    public void clicarSobre(View v){
        redirecionaParaActivity(this, SobreActivity.class, this.nomeUsuario);

    }

    public void clicarConfig(View v){
        redirecionaParaActivity(this, ConfiguracoesActivity.class, this.nomeUsuario);

    }

    public void clicarSair(View v){
        sairApp(this, MainActivity.class);
    }

    public static void redirecionaParaActivity(Activity essa, Class aquela, String nomeUsuario){
        Intent i = new Intent(essa, aquela);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("nome_usuario", nomeUsuario);
        essa.startActivity(i);
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
                nomeUsuario = data.getStringExtra("nome_usuario");
                String msgBoasVindas = R.string.txt_tv_bemVindo + nomeUsuario;
                tv_bemVindo.setText(msgBoasVindas);
                atualizaListView();
       }
    }

    protected void  onPause() {
        fecharMenu(drawerLayout);
        super.onPause();
    }

}