package com.example.projetoEpagri.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.projetoEpagri.BancoDeDadosSchema.IPropriedadeSchema;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalAnimais;
import com.example.projetoEpagri.BancoDeDadosSchema.ITotalPiqueteMes;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Classes.ListViewPropriedadesAdapter;
import com.example.projetoEpagri.Classes.Propriedade;
import com.example.projetoEpagri.Classes.Tutorial;
import com.example.projetoEpagri.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IndexFragment extends Fragment {
    private ListView lv_propriedades;
    private ListViewPropriedadesAdapter listview_adapter;
    private DrawerLayout drawer_layout;

    //Estrutura compartilhada para armazenamento dos dados no banco. Vão sendo preenchidos aos poucos conforme os fragments.
    public static Propriedade propriedade; //Propriedade a ser criada quando o usuário clica no botão cadastrar propriedade.
    public static ArrayList<Double> listaTotaisMes;
    public static ArrayList<Double> listaTotaisEstacoes;
    public static ArrayList<Double> listaTotalUAHA;

    public static Tutorial tutorial;

    public IndexFragment() {
    }

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final ImageView iv_vaca = getView().findViewById(R.id.iv_vaca);
        lv_propriedades = getView().findViewById(R.id.lv_propriedades);
        final Button bt_cadastrarPropriedade = getView().findViewById(R.id.bt_cadastrar_propriedade);

        //Recupera as propriedades do usuário baseado no seu ID.
        ArrayList<Propriedade> lista_propriedades = BancoDeDados.propriedadeDAO.getAllPropriedadesByUserId(IndexActivity.usuario.getId());
        listview_adapter = new ListViewPropriedadesAdapter(getContext(), lista_propriedades);
        lv_propriedades.setAdapter(listview_adapter);

        //Drawer menu.
        drawer_layout = getView().findViewById(R.id.drawerLayout);
        final View menu_drawer = getView().findViewById(R.id.included_nav_drawer);
        final LinearLayout ll_menu_inicio = getView().findViewById(R.id.ll_menu_inicio);

        TextView tv_bem_vindo = getView().findViewById(R.id.tv_tituloIndex);
        String msg_boas_vindas = getString(R.string.txt_tv_bemVindo) + " " + IndexActivity.usuario.getNome() + "!";
        tv_bem_vindo.setText(msg_boas_vindas);

        //Se existirem propriedades, esconde o textview "Bem vindo Usuário"
        if (lista_propriedades.size() > 0) {
            tv_bem_vindo.setVisibility(View.INVISIBLE);
        }

        registerForContextMenu(lv_propriedades);

        TextView nome;
        nome = menu_drawer.findViewById(R.id.tv_nomeDinamico);
        nome.setText(IndexActivity.usuario.getNome());

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

        //Mostra o tutorial para o usuário.
        tutorial = MainActivity.bancoDeDados.tutorialDAO.getTutorial();

        if (tutorial != null && tutorial.getIntroducao_index() == 0) {
            iv_vaca.setVisibility(View.VISIBLE);

            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(iv_vaca, "Olá!", "Seja bem vindo ao PlanPec! \nParece que é a sua primeira vez por aqui, vamos conhecer o app? \n\nPara continuar com o tutorial clique na imagem da vaca, para cancelar o tutorial clique fora do círculo verde.")
                                    .id(1)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false)
                                    .cancelable(true),
                            TapTarget.forView(iv_menu, "Menu Lateral", "Clicando nesse ícone você tem acesso ao menu lateral.\n\nVocê também pode abrir o menu deslizando o dedo para a direita e fechar deslizando o dedo para a esquerda.")
                                    .id(2)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(iv_vaca, "Cadastrar Propriedade", "O primeiro passo no PlanPec é o cadastro de propriedades. \n\nPara isto, basta clicar no botão \"Cadastrar Propriedade\" logo abaixo...")
                                    .id(3)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false)
                                    .transparentTarget(true))
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            iv_vaca.setVisibility(View.GONE);
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 0, 0, 0, 0);
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                            switch (lastTarget.id()) {
                                case 1:
                                    iv_vaca.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    iv_vaca.setVisibility(View.VISIBLE);
                                    break;
                                case 3:
                                    iv_vaca.setVisibility(View.GONE);
                                    break;
                            }
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            iv_vaca.setVisibility(View.GONE);
                            MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 0, 0, 0, 0);
                        }
                    }
            ).start();
        }

        //Testa também a tabela de propriedades pois o usuário pode cancelar o primeiro tutorial, abrir o menu e voltar para a index.
        //Se isso ocorre, o introducao_index é true e o conclusao_index é falso, sendo assim, o tutorial seria mostrado no momento errado.
        if (tutorial != null && tutorial.getIntroducao_index() == 1 && tutorial.getConclusao_index() == 0 &&
                !MainActivity.bancoDeDados.verificaTabelaVazia(IPropriedadeSchema.TABELA_PROPRIEDADE)) {
            iv_vaca.setVisibility(View.VISIBLE);

            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(iv_vaca, "Parabéns!", "Você cadastrou uma propriedade!\n\nTodas as propriedades cadastradas serão listadas nessa tela.")
                                    .id(1)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false)
                                    .cancelable(true),
                            TapTarget.forView(lv_propriedades, "Menu de Opções", "Clicando sobre a propriedade cadastrada você tem acesso a um menu com algumas opções.\n\nVocê pode editar os dados cadastrados, fazer uma proposta de planejamento forrageiro ou acessar os gráficos de oferta e demanda da propriedade\n\nMas atenção!!! O gráfico da proposta só pode ser visualizado após uma proposta de planejamento ser realizada.")
                                    .id(2)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(iv_vaca, "Excluir Propriedade", "Você também pode excluir uma propriedade cadastrada clicando na imagem da lixeira.")
                                    .id(3)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false),
                            TapTarget.forView(iv_vaca, "Tutorial Finalizado", "Parabéns! Você finalizou o tutorial com sucesso.\n\nAgora você já sabe como utilizar o PlanPec. Muuuu!")
                                    .id(4)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.branco)
                                    .textColor(R.color.branco)
                                    .tintTarget(false))
                    .listener(new TapTargetSequence.Listener() {

                                  @Override
                                  public void onSequenceFinish() {
                                      iv_vaca.setVisibility(View.GONE);
                                      MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 1, 1, 1);
                                  }

                                  @Override
                                  public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                                      switch (lastTarget.id()) {
                                          case 1:
                                              iv_vaca.setVisibility(View.INVISIBLE);
                                              break;
                                          case 2:
                                              iv_vaca.setVisibility(View.VISIBLE);
                                              break;
                                      }
                                  }

                                  @Override
                                  public void onSequenceCanceled(TapTarget lastTarget) {
                                      iv_vaca.setVisibility(View.GONE);
                                      MainActivity.bancoDeDados.tutorialDAO.update(1, 1, 1, 1, 1, 1);
                                  }
                              }
                    ).start();
        }
    }

    /**
     * Método responsável por criar o menu ao realizar um click longo em uma propriedade da lista.
     *
     * @param menu     .
     * @param v        .
     * @param menuInfo .
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_propriedade, menu);
        menu.setHeaderTitle("Selecione uma opção");
    }

    /**
     * Método responsável pelo clique em uma das opções do menu.
     *
     * @param item Item do menu.
     * @return True caso seja clicado e false caso contrário.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Recebe as informações para qual item da lista o menu está sendo exibido.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Propriedade propriedade = (Propriedade) listview_adapter.getItem(info.position);
        int id_propriedade = BancoDeDados.propriedadeDAO.getPropriedadeId(propriedade.getNome());

        if (item.getItemId() == R.id.mi_ver_dados) {
            IndexFragment.propriedade = new Propriedade(propriedade.getNome(), propriedade.getRegiao());
            Fragment ver_dados_fragment = VerDadosFragment.newInstance(propriedade.getNome(), false);
            MainActivity.startFragment(ver_dados_fragment, "ver_dados_fragment", R.id.ll_index, true, true, getActivity());
        } else if (item.getItemId() == R.id.mi_fazer_proposta) {
            IndexFragment.propriedade = new Propriedade(propriedade.getNome(), propriedade.getRegiao());
            Fragment ver_dados_fragment = VerDadosFragment.newInstance(propriedade.getNome(), true);
            MainActivity.startFragment(ver_dados_fragment, "ver_dados_fragment", R.id.ll_index, true, true, getActivity());
        } else if (item.getItemId() == R.id.mi_ver_grafico_atual) {
            Fragment grafico_fragment = GraficoFragment.newInstance(id_propriedade, "atual");
            MainActivity.startFragment(grafico_fragment, "grafico_fragment", R.id.ll_index, true, true, getActivity());
        } else if (item.getItemId() == R.id.mi_ver_grafico_proposta) {
            //Arrumar um jeito de verificar se as tabelas possuem entradas com o id da propriedade ou não.

            if (MainActivity.bancoDeDados.verificaConteudoTabelaById(id_propriedade, ITotalPiqueteMes.TABELA_TOTAL_PIQUETE_MES_PROPOSTA, ITotalPiqueteMes.COLUNA_ID_PROPRIEDADE) &&
                    MainActivity.bancoDeDados.verificaConteudoTabelaById(id_propriedade, ITotalAnimais.TABELA_TOTAL_ANIMAIS_PROPOSTA, ITotalAnimais.COLUNA_ID_PROPRIEDADE)) {
                Fragment grafico_fragment = GraficoFragment.newInstance(id_propriedade, "proposta");
                MainActivity.startFragment(grafico_fragment, "grafico_fragment", R.id.ll_index, true, true, getActivity());
            } else {
                Toast.makeText(getActivity(), "Faça a proposta para poder visualizar o gráfico!", Toast.LENGTH_LONG).show();
            }
        } else {
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

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}