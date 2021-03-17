package com.example.projetoEpagri.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Activities.IndexActivity;
import com.example.projetoEpagri.Activities.MainActivity;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteEstacao;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.ListViewPropriedadesAdapter;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.Classes.Usuario;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class IndexFragment extends Fragment {
    private int id_usuario;
    private ArrayList<Propriedade> lista_propriedades;
    private ListView lv_propriedades;
    private ListViewPropriedadesAdapter listview_adapter;
    private DrawerLayout drawer_layout;
    private TextView tv_bem_vindo;

    //Estrutura compartilhada para armazenamento dos dados no banco. Vão sendo preenchidos aos poucos conforme os fragments.
    public static Propriedade propriedade; //Propriedade a ser criada quando o usuário clica no botão cadastrar propriedade.
    public static ArrayList<Double> listaTotaisMes;
    public static ArrayList<Double> listaTotaisEstacoes;
    public static ArrayList<Double> listaTotalUAHA;

    public IndexFragment() {}

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recupera o id do usuário baseado no seu nome.
        id_usuario = BancoDeDados.usuarioDAO.getUSuarioId(IndexActivity.nome_usuario);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView iv_menu = getView().findViewById(R.id.iv_menu);
        lv_propriedades = getView().findViewById(R.id.lv_propriedades);
        final Button bt_cadastrarPropriedade = getView().findViewById(R.id.bt_cadastrar_propriedade);

        //Recupera as propriedades do usuário baseado no seu ID.
        lista_propriedades = BancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(id_usuario);
        listview_adapter = new ListViewPropriedadesAdapter(getContext(), lista_propriedades);
        lv_propriedades.setAdapter(listview_adapter);

        //Drawer menu.
        drawer_layout = getView().findViewById(R.id.drawerLayout);
        final View menu_drawer = getView().findViewById(R.id.included_nav_drawer);
        final LinearLayout ll_menu_inicio = getView().findViewById(R.id.ll_menu_inicio);

        tv_bem_vindo = getView().findViewById(R.id.tv_tituloIndex);
        String msg_boas_vindas = getString(R.string.txt_tv_bemVindo) + " " + IndexActivity.nome_usuario + "!";
        tv_bem_vindo.setText(msg_boas_vindas);

        //Se existirem propriedades, esconde o textview "Bem vindo Usuário"
        if(lista_propriedades.size() > 0){
            tv_bem_vindo.setVisibility(View.INVISIBLE);
        }

        registerForContextMenu(lv_propriedades);

        //Mostra nome e e-mail do usuário no drawer layout.
        Usuario usuario = BancoDeDados.usuarioDAO.getUsuario(id_usuario);
        TextView nome, email;
        nome = menu_drawer.findViewById(R.id.tv_nomeDinamico);
        email = menu_drawer.findViewById(R.id.tv_emailDinamico);
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());

        //Clique no ícone do menu.
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexActivity.abrirMenu(drawer_layout);
            }
        });

        //Clique no item "Início" do menu.
        ll_menu_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               IndexActivity.fecharMenu(drawer_layout);
            }
        });

        //Clique em alguma propriedade da lista.
        lv_propriedades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv_propriedades.showContextMenuForChild(view);
            }
        });

        //Clique no botão "Cadastrar Propriedade".
        bt_cadastrarPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment criar_propriedade_fragment = CriarPropriedadeFragment.newInstance();
                MainActivity.startFragment(criar_propriedade_fragment, "criar_propriedade_fragment", R.id.ll_index, true, true, getActivity());
            }
        });
    }

    /**
     * Método responsável por criar o menu ao realizar um click longo em uma propriedade da lista.
     * @param menu .
     * @param v .
     * @param menuInfo .
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo){
        getActivity().getMenuInflater().inflate(R.menu.menu_propriedade, menu);
        menu.setHeaderTitle("Selecione uma opção");
    }

    /**
     * Método responsável pelo clique em uma das opções do menu.
     * @param item Item do menu.
     * @return True caso seja clicado e false caso contrário.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //Recebe as informações para qual item da lista o menu está sendo exibido.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Propriedade propriedade = (Propriedade) listview_adapter.getItem(info.position);
        int id_propriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());

        if(item.getItemId() == R.id.mi_ver_dados){
            IndexFragment.propriedade = new Propriedade(propriedade.getNome(), propriedade.getRegiao());
            Fragment ver_dados_fragment = VerDadosFragment.newInstance(propriedade.getNome(), false);
            MainActivity.startFragment(ver_dados_fragment, "ver_dados_fragment", R.id.ll_index, true, true, getActivity());
        }
        else if(item.getItemId() == R.id.mi_fazer_proposta) {
            IndexFragment.propriedade = new Propriedade(propriedade.getNome(), propriedade.getRegiao());
            Fragment ver_dados_fragment = VerDadosFragment.newInstance(propriedade.getNome(), true);
            MainActivity.startFragment(ver_dados_fragment, "ver_dados_fragment", R.id.ll_index, true, true, getActivity());
        }
        else if(item.getItemId() == R.id.mi_ver_grafico_atual){
            Fragment grafico_fragment = GraficoFragment.newInstance(id_propriedade, "atual");
            MainActivity.startFragment(grafico_fragment, "grafico_fragment", R.id.ll_index, true, true, getActivity());
        }
        else if(item.getItemId() == R.id.mi_ver_grafico_proposta){
            //Arrumar um jeito de verificar se as tabelas possuem entradas com o id da propriedade ou não.

            if(MainActivity.bancoDeDados.verificaConteudoTabelaById(id_propriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA, ITotalPiqueteMes.COLUNA_ID_PROPRIEDADE) &&
               MainActivity.bancoDeDados.verificaConteudoTabelaById(id_propriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA, ITotalAnimais.COLUNA_ID_PROPRIEDADE)){
                Fragment grafico_fragment = GraficoFragment.newInstance(id_propriedade,  "proposta");
                MainActivity.startFragment(grafico_fragment, "grafico_fragment", R.id.ll_index, true, true, getActivity());
            }
            else{
                Toast.makeText(getActivity(), "Faça a proposta para poder visualizar o gráfico!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            return false;
        }
        return true;
    }

    /**
     * Método utilizado para fechar o menu quando um novo fragment é exibido.
     */
    public void onPause() {
        IndexActivity.fecharMenu(drawer_layout);
        super.onPause();
    }
}