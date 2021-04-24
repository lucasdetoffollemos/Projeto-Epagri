package com.example.projetoEpagri.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetoEpagri.BancoDeDadosSchema.IDadosSchema;
import com.example.projetoEpagri.Classes.BancoDeDados;
import com.example.projetoEpagri.Fragments.LoginFragment;
import com.example.projetoEpagri.Fragments.SplashScreenFragment;
import com.example.projetoEpagri.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static BancoDeDados bancoDeDados;
    public int codigoReq = 1;
    private String versao;
    private boolean versaoDiferente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bancoDeDados = new BancoDeDados(this);
        bancoDeDados.abreConexao();

        versao = "";
        versaoDiferente = false;

        new BackgroundTask().execute();

        //pegando o código que vem da index activity
        Intent intent = getIntent();
        codigoReq = intent.getIntExtra("cod", 2);

        //se o código for diferente de 1, que é o código vindo da index, mostrar a splash screen, se o código for igual a 1, ou seja o código veio da index, nao mostrar a splash screen.
        if(codigoReq != 1){
            Fragment fragment = SplashScreenFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.ll_main, fragment, "splash_screen_fragment");
            transaction.commit();
        }
        else{
            Fragment main_fragment = LoginFragment.newInstance();
            MainActivity.startFragment(main_fragment, "main_fragment", R.id.ll_main, false, false, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void inserirDadosOfflineTabelaPastagemNorte(){
        //Dados Norte - cfa
        BancoDeDados.dadosDAO.inserirPastagem("Andropogon", 4.0, 10.9, 15.0, new int[]{20, 15, 7, 3, 2, 2, 1, 2, 3, 10, 15, 20}, 100, IDadosSchema.TABELA_DADOS_NORTE);
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
    public void inserirDadosOfflineTabelaPastagemSul(){
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

    /**
     * Método responsável por verificar se existe uma conexão com a internet.
     * @return Retorna o status da conexão.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    /**
     * Método para verificar se existe o arquivo de dados ou não.
     * @return Retorna true caso o arquivo especificado exista e false caso contrário.
     */
    public boolean arquivoDeDadosExists(File file){
        return file.exists();
    }

    //Classe utilizada para acessar o servidor e realizar o download do arquivo em background.
    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        String text = "";

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(getApplicationContext().getFilesDir(),"planpec_data.txt");

            //Se o arquivo de dados existir (já foi feito o download anteriormente), verifica qual é a versão.
            if(arquivoDeDadosExists(file)){
                Log.i("arq", "arquivo existe");
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    versao = br.readLine();
                    br.close();
                    Log.i("versao", versao);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Log.i("arq", "arquivo não existe");
            }

            URL url;
            //if(isNetworkAvailable()){
                try {
                    url = new URL("http://labegen3.cppsul.embrapa.br/planpec/planpec_data.txt");
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                    String line;

                    while ((line = in.readLine()) != null) {
                        if(line.equals(versao)){
                            versaoDiferente = false;
                            break;
                        }
                        else{
                            versaoDiferente = true;
                            text += line + "\n";
                        }

                    }
                    in.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //}
            //else{
            //    Log.i("internet", "Sem conexão com a internet!");
            //}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if(versaoDiferente){
                    Log.i("vdif", "as versões sao diferentes, escrevendo novo arquivo");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("planpec_data.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(text);
                    outputStreamWriter.close();

                    File file = new File(getApplicationContext().getFilesDir(),"planpec_data.txt");
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String linha;
                    ArrayList<String> conteudoArquivo = new ArrayList<>();

                    if(bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_NORTE) && bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_SUL)){
                        bancoDeDados.dadosDAO.deleteAllDados(IDadosSchema.TABELA_DADOS_NORTE);
                        bancoDeDados.dadosDAO.deleteAllDados(IDadosSchema.TABELA_DADOS_SUL);
                    }

                    while((linha = br.readLine()) != null){
                        Log.i("txt", linha);
                        linha = linha.replaceAll("\\s+","");
                        conteudoArquivo.add(linha);
                    }
                    br.close();

                    //Varíaveis para indicar em qual tabela será inserida as pastagens.
                    boolean cfa = false, cfb = false;

                    //Inicia em 1 para pular a primeira linha do arquivo (que mostra a versão).
                    for(int i=1; i<conteudoArquivo.size(); i++){
                        Log.i("aaaa", conteudoArquivo.get(i));
                        Log.i("tamanho", String.valueOf(conteudoArquivo.size()));

                        if (conteudoArquivo.get(i).contains("CFA")){
                            Log.i("cfa", "Achei CFA");
                            cfa = true;
                            cfb = false;
                        }
                        else if(conteudoArquivo.get(i).contains("CFB")){
                            Log.i("cfb", "Achei CFB");
                            cfa = false;
                            cfb = true;
                        }
                        else{
                            Log.i("cfc", "Não achei nada");
                            //Quebra cada linha do arquivo (nas vírgulas) em pedaços.
                            String[] parts = conteudoArquivo.get(i).split(",");

                            int[] meses = {Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]),
                                    Integer.parseInt(parts[8]), Integer.parseInt(parts[9]), Integer.parseInt(parts[10]), Integer.parseInt(parts[11]),
                                    Integer.parseInt(parts[12]), Integer.parseInt(parts[13]), Integer.parseInt(parts[14]), Integer.parseInt(parts[15])};

                            //Insere pastagens na tabela dados norte.
                            if(cfa){
                                bancoDeDados.dadosDAO.inserirPastagem(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                                        meses, Integer.parseInt(parts[16]), IDadosSchema.TABELA_DADOS_NORTE);
                            }

                            //Insere pastagens na tabela dados sul.
                            if(cfb) {
                                bancoDeDados.dadosDAO.inserirPastagem(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                                        meses, Integer.parseInt(parts[16]), IDadosSchema.TABELA_DADOS_SUL);
                            }
                        }
                    }
                }
                else{
                    //Esse caso ocorre caso o usuário execute o app pela primeira vez e não tenha conexão com a internet.
                    //Como o arquivo de dados não será encontrado, o valor da varíavel "versão diferente" permanecerá com o valor false
                    //e cairá nesse else. Verifica-se então se as tabelas de dados estão vazias, em caso positivo, utiliza-se os dados offline.
                    if(bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_NORTE) && bancoDeDados.verificaTabelaVazia(IDadosSchema.TABELA_DADOS_NORTE)){
                        inserirDadosOfflineTabelaPastagemNorte();
                    }

                    if(bancoDeDados.verificaExistenciaTabela(IDadosSchema.TABELA_DADOS_SUL) && bancoDeDados.verificaTabelaVazia(IDadosSchema.TABELA_DADOS_SUL)) {
                        inserirDadosOfflineTabelaPastagemSul();
                    }

                    Log.i("vdif", "as versões sao iguais");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.e("Erro", "Erro na escrita do arquivo: " + e.toString());
            }
        }
    }
}
