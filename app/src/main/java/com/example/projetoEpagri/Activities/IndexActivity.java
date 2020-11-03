package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.ListViewPropriedadesAdapter;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {
    private TextView tv_bemVindo;
    private ArrayList<Propriedade> listaPropriedade;
    private ListView lv_propriedades;
    private ListViewPropriedadesAdapter listViewPropriedadesAdapter;
    private Button bt_cadastrarPropriedade;
    private String nomeUsuario;
    private int codigoRequisicao = 1; //Código para identificar a activity no método onActivityResult.
    private int usuarioId;

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
    public void inicializa(){
        //Recebe a mensagem (nome do usuário) da activity anterior (LoginActivity).
        Intent intent = getIntent();
        nomeUsuario = intent.getStringExtra("nome_usuario");

        //Toast.makeText(IndexActivity.this, nomeUsuario, Toast.LENGTH_SHORT).show();

        tv_bemVindo = findViewById(R.id.tv_tituloIndex);
        tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);

        lv_propriedades = findViewById(R.id.lv_propriedades);
        bt_cadastrarPropriedade = findViewById(R.id.bt_levaPropriedade);

        //Recupera o ID do usuário baseado no nome recebido da activity Main.
        usuarioId = MainActivity.bancoDeDados.usuarioDAO.getUSuarioId(nomeUsuario);
        //Recupera as propriedades do usuário baseado no seu ID.
        listaPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(usuarioId);

        //Se existirem propriedades, esconde o textview "Bem vindo Usuário"
        if(listaPropriedade.size() > 0){
            tv_bemVindo.setVisibility(View.INVISIBLE);
        }

        listViewPropriedadesAdapter = new ListViewPropriedadesAdapter(this, listaPropriedade);
        lv_propriedades.setAdapter(listViewPropriedadesAdapter);
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
        listaPropriedade = MainActivity.bancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(usuarioId);
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
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.codigoRequisicao) {
            if(resultCode == Activity.RESULT_OK){
                nomeUsuario = data.getStringExtra("nome_usuario");
                tv_bemVindo.setText("Seja bem vindo " + nomeUsuario);

                atualizaListView();
                Toast.makeText(this.getApplicationContext(), nomeUsuario, Toast.LENGTH_SHORT).show();

                /*for(int i=0; i<listaPropriedade.size(); i++){
                    Log.i("listaPropriedade", listaPropriedade.get(i).getNome() + " "
                            + listaPropriedade.get(i).getArea() + " "
                            + listaPropriedade.get(i).getQtdeAnimais());
                }*/
            }

            if (resultCode == Activity.RESULT_CANCELED) {}
        }
    }
}