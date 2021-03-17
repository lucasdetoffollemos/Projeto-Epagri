package com.example.projetoEpagri.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Fragments.SplashScreenFragment;
import com.example.projetoEpagri.R;

public class MainActivity extends AppCompatActivity{
    public static BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bancoDeDados = new BancoDeDados(this);
        bancoDeDados.abreConexao();

        //Verifica a existência das tabelas de dados e faz a criação caso necessário.
        if(bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_NORTE) && bancoDeDados.verificaTabelaVazia(IDadosSchema.TABELA_DADOS_NORTE)){
            inseriDadosTabelaPastagemNorte();
        }
        if(bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_SUL) && bancoDeDados.verificaTabelaVazia(IDadosSchema.TABELA_DADOS_SUL)){
            inseriDadosTabelaPastagemSul();
        }

        Fragment fragment = SplashScreenFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_main, fragment, "splash_screen_fragment");
        transaction.commit();
    }

    /**
     * Método responsável por iniciar um fragment.
     * @param fragment Fragment.
     * @param tag Tag do fragment.
     * @param id Id do elemento de layout onde o fragment aparecerá.
     * @param backStack Indica se o fragment deve aparecer na pilha ou não.
     * @param animations Indica se o fragment terá animação de entrada e saída.
     * @param activity Representa a activity onde o fragment será exibido.
     */
    public static void startFragment(Fragment fragment, String tag, int id, boolean backStack, boolean animations, Activity activity) {
        FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();

        if(animations){
            transaction.setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            );
        }

        transaction.replace(id, fragment, tag);

        if(backStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    /**
     * Método responsável por inserir os dados das pastagem do cfa (norte) na tabela.
     */
    public void inseriDadosTabelaPastagemNorte(){
        //Dados Norte - cfa
        bancoDeDados.dadosDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Azevém", 2.0, 4.0, 7.0, new int[]{0, 0, 0, 0, 3, 22, 30, 30, 10, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("B. Brizanta", 5.0, 12, 23.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("B. Decumbens", 4.0, 12.8, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{20, 15, 15, 15, 5, 2, 1, 2, 3, 5, 7, 10}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Estrela Africana", 4.0, 11.8, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{25, 15, 10, 0, 0, 0, 0, 0, 0, 0, 20, 30}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Tifthon 85", 6.0, 15.0, 25.0, new int[]{17, 13, 7, 6, 4, 3, 2, 4, 8, 9, 13, 14}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Triticale", 2.0, 4.0, 12.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        //bancoDeDados.dadosDAO.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);//(Nao esta toda preenchida no excel)
        //bancoDeDados.dadosDAO.inserirPastagem("Alto Grao", 0.0, 0.0, 25.0, new int[]{0, 0, 34, 33, 33, 0, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_NORTE);//(Nao esta toda preenchida no excel)
        bancoDeDados.dadosDAO.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100, IDadosSchema.TABELA_DADOS_NORTE);
        bancoDeDados.dadosDAO.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_NORTE);
    }

    /**
     * Método responsável por inserir os dados das pastagem do cfb (sul) na tabela.
     */
    public void inseriDadosTabelaPastagemSul(){
        //Dados Sul - cfb
        bancoDeDados.dadosDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Angola", 3.0, 9.7, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia Branca", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia Preta", 2.0, 5.0, 8.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Aveia + Azevém", 2.0, 5.0, 9.0, new int[]{0, 0, 0, 0, 5, 25, 25, 25, 15, 5, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Azevém", 2.0, 4.0, 9.0, new int[]{0, 0, 0, 5, 10, 20, 20, 20, 15, 10, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("B. Brizanta", 5.0, 11.3, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("B. Decumbens", 4.0, 12.8, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("B. Humidícola", 3.0, 7.0, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Ruziziensis", 4.0, 9.5, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Coast Cross 1", 3.0, 11.4, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Colonião", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Elefante", 4.0, 10.0, 25.0, new int[]{25, 25, 10, 2, 0, 0, 0, 0, 0, 3, 15, 20}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Estrela Africana", 4.0, 11.8, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Gordura", 4.0, 4.2, 7.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Jaraguá", 3.0, 8.1, 12.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Milheto", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Mombaça", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Pangola", 4.0, 5.9, 16.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Pensacola", 3.0, 6.0, 18.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Quicuio", 4.0, 7.0, 20.0, new int[]{20, 20, 10, 5, 0, 0, 0, 0, 3, 7, 15, 20}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Rhodes", 4.0, 7.3, 15.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Setaria", 4.0, 6.6, 15.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Tanzânia", 4.0, 9.9, 18.0, new int[]{22, 18, 8, 2, 0, 0, 0, 0, 2, 5, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Tifthon 85", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Triticale", 2.0, 4.0, 11.0, new int[]{0, 0, 0, 0, 10, 25, 30, 25, 10, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);
        //bancoDeDados.dadosDAO.inserirPastagem("Silagem", 15.0, 20.0, 25.0, new int[]{0, 0, 0, 30, 40, 30, 0, 0, 0, 0, 0, 0}, 100, IDadosSchema.TABELA_DADOS_SUL);//(Nao esta toda preenchida no excel)
        bancoDeDados.dadosDAO.inserirPastagem("Pastagem Naturalizada", 2.0, 5.0, 11.0, new int[]{15, 14, 10, 7, 2, 0, 0, 5, 8, 10, 14, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Perene de inverno", 3.0, 6.0, 12.0, new int[]{10, 9, 8, 7, 7, 6, 6, 7, 9, 11, 10, 10}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Sudão", 5.0, 8.0, 18.0, new int[]{30, 30, 15, 5, 0, 0, 0, 0, 0, 0, 5, 15}, 100, IDadosSchema.TABELA_DADOS_SUL);
        bancoDeDados.dadosDAO.inserirPastagem("Outra", 3.0, 5.2, 20.0, new int[]{20, 15, 7, 3, 0, 0, 0, 0, 2, 10, 21, 22}, 100, IDadosSchema.TABELA_DADOS_SUL);
    }
}
