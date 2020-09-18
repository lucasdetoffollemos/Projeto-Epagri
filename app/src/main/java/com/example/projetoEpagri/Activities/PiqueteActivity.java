package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.Classes.Animais;
import com.example.projetoEpagri.Classes.Piquete;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PiqueteActivity extends AppCompatActivity{
    private  Button bt_adicionar_linha, bt_remover_linha;
    public int i=-1, numeroDeLinhas=0;
    private  TableRow linha_tabela;
    private TableLayout table_layout;
    private DadosSulDAO dadosSulDAO;
    private ArrayList<Double> listaDeAreas, listaJan, listaFev, listaMar, listaAbr, listaMai, listaJun, listaJul, listaAgo, listaSet, listaOut, listaNov, listaDez, listaVerao, listaOutono, listaInverno, listaPrimavera;



    //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
    private String tipo, condicao, areaS;
    private double areaD;
    private Spinner sp_tipo, sp_condicao;
    private EditText et_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);
        listaDeAreas = new ArrayList();
        listaJan = new ArrayList();
        listaFev = new ArrayList();
        listaMar = new ArrayList();
        listaAbr = new ArrayList();
        listaMai = new ArrayList();
        listaJun = new ArrayList();
        listaJul = new ArrayList();
        listaAgo = new ArrayList();
        listaSet = new ArrayList();
        listaOut = new ArrayList();
        listaNov = new ArrayList();
        listaDez = new ArrayList();
        listaVerao = new ArrayList();
        listaOutono = new ArrayList();
        listaInverno = new ArrayList();
        listaPrimavera = new ArrayList();


        //Método que tem o spinner para selecionar qual regiao o usário deseja
        escolherRegiao();

        dadosSulDAO = new DadosSulDAO(PiqueteActivity.this);

        table_layout = (TableLayout) findViewById(R.id.table_layout);


        bt_adicionar_linha = findViewById(R.id.bt_adicionar_linha);
        //Quando clicado no botao de mais, é acionado está funçao.
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarLinhaTabela();
                i++;
                numeroDeLinhas++;
                //Toast.makeText(getApplicationContext(), "OI", Toast.LENGTH_SHORT).show();
            }
        });

        //Quando clicado no botao de menos, é acionado está funçao, que tem como objetivo excluir cada linha da tabela.
        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_layout.removeView(table_layout.getChildAt(i));
                i--;

                if(i >= -1){
                    listaDeAreas.remove(numeroDeLinhas-1);
                    listaJan.remove(numeroDeLinhas-1);
                    listaFev.remove(numeroDeLinhas-1);
                    listaMar.remove(numeroDeLinhas-1);
                    listaAbr.remove(numeroDeLinhas-1);
                    listaMai.remove(numeroDeLinhas-1);
                    listaJun.remove(numeroDeLinhas-1);
                    listaJul.remove(numeroDeLinhas-1);
                    listaAgo.remove(numeroDeLinhas-1);
                    listaSet.remove(numeroDeLinhas-1);
                    listaOut.remove(numeroDeLinhas-1);
                    listaNov.remove(numeroDeLinhas-1);
                    listaDez.remove(numeroDeLinhas-1);
                    listaVerao.remove(numeroDeLinhas-1);
                    listaOutono.remove(numeroDeLinhas-1);
                    listaInverno.remove(numeroDeLinhas-1);
                    listaPrimavera.remove(numeroDeLinhas-1);
                    numeroDeLinhas--;
                    calculaTotais(-2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                }

                else{
                   i++;
                }
                //ERRO NO CÓDIGO, AOS SER CLICADO DUAS VEZES NO BOTAO DE MENOS




                //Toast.makeText(getApplicationContext(), "tCHAU", Toast.LENGTH_SHORT).show();
            }
        });


//        bt_proximo_passo = findViewById(R.id.bt_proximo_passo);
//        bt_proximo_passo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                irParaAnimaisActivity(2.0);
//            }
//        });
    }



    private void escolherRegiao() {

        ArrayList<String> regiaoPiquete = new ArrayList<>();
        regiaoPiquete.add("Sul");
        regiaoPiquete.add("Norte");


        Spinner spinnerRegiaoPiquete = findViewById(R.id.spinnerRegiao);
        ArrayAdapter<String> spinnerRegiaoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, regiaoPiquete);
        spinnerRegiaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegiaoPiquete.setAdapter(spinnerRegiaoAdapter);
    }

    /**
     * Método responsável por adicionar uma linha na tabela oferta atual e configurar o adapter dos spinners.
     */
    private void adicionarLinhaTabela(){
        //Infla a linha para a tabela
        linha_tabela = (TableRow) View.inflate(PiqueteActivity.this, R.layout.tabela_oferta_atual_linha, null);

        // Array que armazena os tipos de piquetes, vindos do arquivo DadosSulDAO.java.
        ArrayList<String> tipoPiquete = dadosSulDAO.getTiposPastagem();
        //Localiza o spinner tipo no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinnerTipoPiquete);
        //Cria um ArrayAdpter usando o array de string com os tipos armazenados no banco de dados.
        ArrayAdapter<String> spinnerTipoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, tipoPiquete);
        spinnerTipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPiquete.setAdapter(spinnerTipoAdapter);

        //Criando um array de String para as condiçoes de pastagem.
        ArrayList<String> condicaoPiquete = new ArrayList<>();
        condicaoPiquete.add("Degradada");
        condicaoPiquete.add("Média");
        condicaoPiquete.add("Ótima");

        //Localiza o spinner condicao no arquivo xml tabela_oferta_atual_linha.
        Spinner spinnerCondicaoPiquete = linha_tabela.findViewById(R.id.spinnerCondPiquete);
        //Cria um ArrayAdpter usando o array de string com condicoes "degradada", "média" e "ótima". //Cria um ArrayAdapter que pega o Array de string "condicaoPiquete" e transforma em um spinner.
        ArrayAdapter<String> spinnerCondicaoAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, condicaoPiquete);
        spinnerCondicaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicaoPiquete.setAdapter(spinnerCondicaoAdapter);

        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(i);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);

        identificaElementosDaLinha(linha_tabela);
    }






    /**
     * Identifica os elementos dentro da TableRow que foi inflada e chama o método de calcular quando algum valor
     * é escolhido nos spinners ou texto digitado no campo área.
     * @param linha
     */
    private void identificaElementosDaLinha(final TableRow linha) {
        final Spinner spinnerTipo = (Spinner) linha.getChildAt(0);
        final Spinner spinnerCondicao = (Spinner) linha.getChildAt(1);
        final EditText editTextArea = (EditText) linha.getChildAt(2);



        //Quando o spinner TIPO for clicado, a funçao ira convereter o spinner para string, e logo depois ira chamar a funçao calcular().
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;
                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }

                calcular(linha, tipo, condicao, areaD);

            }




            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        //Quando o spinner CONDIÇAO for clicado, a funçao ira converter o spinner para string, e logo depeois chamar a funçao calcular()
        spinnerCondicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;

                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }

                calcular(linha, tipo, condicao, areaD);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Quando o usuário digitar um número no campo Área, esta funçao ira converter o valor recebido por ele, e será chamadoa funçao calcular().
        editTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sp_tipo = spinnerTipo;
                tipo = sp_tipo.getSelectedItem().toString();

                sp_condicao = spinnerCondicao;
                condicao = sp_condicao.getSelectedItem().toString();

                et_area = editTextArea;
                areaS = et_area.getText().toString();

                if(areaS.length() > 0) {
                    areaD = Double.parseDouble(areaS);
                }
                else{
                    areaD = 0;
                }


                calcular(linha, tipo, condicao, areaD);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }



    /*
        primeira linha = -1
        segunda linha = 0
        terceira linha = 1
        Dentro da linha
            spinnerTipo = getchildAt(0)
            spinnerCondicao = getchildAt(1)
            campoArea = getchildAt(2)

    */

    /**
     * Método responsavel, por pegar os valores dos spinners, TIPO e CONDIÇÃO, e pegar o editText ÁREA, e realizar os cáculos de pastagem mensal. Também setar uma tag para cada elemento da linha.
     * @param linha
     * @param tipoPastagem
     * @param condicao
     * @param area
     */
    public  void calcular(final TableRow linha, String tipoPastagem, String condicao, double area) {

        //Arredonda o cálculo para 2 decimais.
         DecimalFormat doisDecimais = new DecimalFormat("#.##");
         Double aproveitamento = 0.60;


//        Toast.makeText(this, "Tipo: " + tipoPastagem + " Cond: " + condicao + " Área: " + area, Toast.LENGTH_SHORT).show();
        //Log.i("CALCULAR", "Tipo: " + tipoPastagem + " Cond: " + condicao + " Área: " + area);

        //TO DO.
        //Aqui é feito o calculo da produçao estimada
        TextView tv_prod = (TextView) linha.getChildAt(3);
        String producaoEstimada = doisDecimais.format((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * area );
        tv_prod.setText(String.valueOf(producaoEstimada));

        //Janeiro

        TextView janeiro = (TextView) linha.getChildAt(4);
        double mesJan = (float)dadosSulDAO.getMeses(1, tipoPastagem)/100;
        double mesJanD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesJan* area);
        String resultadoJan = doisDecimais.format(mesJanD);

        if(mesJan != 0){
            janeiro.setText(String.valueOf(resultadoJan));
        }
        else {
            janeiro.setText(" ");
        }
       //


        //Fevereiro
        TextView fevereiro = (TextView) linha.getChildAt(5);
        double mesFev = (float)dadosSulDAO.getMeses(2, tipoPastagem)/100;
        double mesFevD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesFev* area);
        String resultadoFev = doisDecimais.format(mesFevD);
        if(mesFev != 0){
            fevereiro.setText(String.valueOf(resultadoFev));
        }
        else {
            fevereiro.setText(" ");
        }
        //

        //Março
        TextView marco = (TextView) linha.getChildAt(6);
        double mesMar = (float)dadosSulDAO.getMeses(3, tipoPastagem)/100;
        double mesMarD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesMar* area);
        String resultadoMar = doisDecimais.format(mesMarD);
        if(mesMar != 0){
            marco.setText(String.valueOf(resultadoMar));
        }
        else {
            marco.setText(" ");
        }
        //

        //Abril
        TextView abril = (TextView) linha.getChildAt(7);
        double mesAbr = (float)dadosSulDAO.getMeses(4, tipoPastagem)/100;
        double mesAbrD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesAbr* area);
        String resultadoAbr = doisDecimais.format(mesAbrD);
        if(mesAbr != 0){
            abril.setText(String.valueOf(resultadoAbr));
        }
        else {
            abril.setText(" ");
        }
        //

        //Maio
        TextView maio= (TextView) linha.getChildAt(8);
        double mesMaio = (float)dadosSulDAO.getMeses(5, tipoPastagem)/100;
        double mesMaiD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesMaio* area);
        String resultadoMaio = doisDecimais.format(mesMaiD);
        if(mesMaio != 0){
            maio.setText(String.valueOf(resultadoMaio));
        }
        else {
            maio.setText(" ");
        }
        //


        //Junho
        TextView junho = (TextView) linha.getChildAt(9);
        double mesJunho = (float)dadosSulDAO.getMeses(6, tipoPastagem)/100;
        double mesJunD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesJunho* area);
        String resultadoJunho = doisDecimais.format(mesJunD);
        if(mesJunho != 0){
            junho.setText(String.valueOf(resultadoJunho));
        }
        else {
            junho.setText(" ");
        }
        //


        //Julho
        TextView julho = (TextView) linha.getChildAt(10);
        double mesJul = (float)dadosSulDAO.getMeses(7, tipoPastagem)/100;
        double mesJulD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesJul* area);
        String resultadoJul = doisDecimais.format(mesJulD);
        if(mesJul != 0){
            julho.setText(String.valueOf(resultadoJul));
        }
        else {
            julho.setText(" ");
        }
        //

        //Agosto
        TextView agosto = (TextView) linha.getChildAt(11);
        double mesAgo = (float)dadosSulDAO.getMeses(8, tipoPastagem)/100;
        double mesAgoD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesAgo* area);
        String resultadoAgo = doisDecimais.format(mesAgoD);
        if(mesAgo != 0){
            agosto.setText(String.valueOf(resultadoAgo));
        }
        else {
            agosto.setText(" ");
        }
        //

        //Setembro
        TextView setembro = (TextView) linha.getChildAt(12);
        double mesSet = (float)dadosSulDAO.getMeses(9, tipoPastagem)/100;
        double mesSetD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesSet* area);
        String resultadoSet = doisDecimais.format(mesSetD);
        if(mesSet != 0){
            setembro.setText(String.valueOf(resultadoSet));
        }
        else {
            setembro.setText(" ");
        }
        //


        //Outubro
        TextView outubro = (TextView) linha.getChildAt(13);
        double mesOut = (float)dadosSulDAO.getMeses(10, tipoPastagem)/100;
        double mesOutD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesOut* area);
        String resultadoOut = doisDecimais.format(mesOutD);
        if(mesOut != 0){
            outubro.setText(String.valueOf(resultadoOut));
        }
        else {
            outubro.setText(" ");
        }
        //

        //Novembro
        TextView novembro = (TextView) linha.getChildAt(14);
        double mesNov = (float)dadosSulDAO.getMeses(11, tipoPastagem)/100;
        double mesNovD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesNov* area);
        String resultadoNov = doisDecimais.format(mesNovD);
        if(mesNov != 0){
            novembro.setText(String.valueOf(resultadoNov));
        }
        else {
            novembro.setText(" ");
        }
        //

        //Dezembro
        TextView dezembro = (TextView) linha.getChildAt(15);
        double mesDez = (float)dadosSulDAO.getMeses(12, tipoPastagem)/100;
        double mesDezD = ((dadosSulDAO.getCondicao(tipoPastagem, condicao)) * aproveitamento * mesDez* area);
        String resultadoDez = doisDecimais.format(mesDezD);
        if(mesDez != 0){
            dezembro.setText(String.valueOf(resultadoDez));
        }
        else {
            dezembro.setText(" ");
        }
        //

        //Total
        TextView total = (TextView) linha.getChildAt(16);
        double totalToneladaAnual = mesJanD  + mesFevD + mesMarD + mesAbrD + mesMaiD + mesJunD +  mesJulD + mesAgoD + mesSetD + mesOutD + mesNovD + mesDezD;
        Integer intTotalTonelada = Integer.valueOf((int) totalToneladaAnual);
        String strTotalToneladaAnual = String.valueOf(intTotalTonelada);
        total.setText(strTotalToneladaAnual);


        //Estações
        double estaVerao = mesJanD + mesFevD + mesDezD;
        double estaOutono = mesMarD + mesAbrD + mesMaiD;
        double estaInverno = mesJunD + mesJulD + mesAgoD;
        double estaPrimavera = mesSetD + mesOutD + mesNovD;

        //Chama a função de calcular os totais.
        calculaTotais((Integer) linha.getTag(), area, mesJanD, mesFevD, mesMarD, mesAbrD, mesMaiD, mesJunD, mesJulD, mesAgoD, mesSetD, mesOutD, mesNovD, mesDezD, estaVerao, estaOutono, estaInverno, estaPrimavera);
        //String texto = linha.getTag().toString();
        // Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
    }


    double somaJan = 0.0, somaFev = 0.0, somaMar = 0.0, somaAbr = 0.0, somaMai = 0.0, somaJun = 0.0, somaJul = 0.0, somaAgo = 0.0, somaSet = 0.0, somaOut = 0.0, somaNov = 0.0, somaDez = 0.0;
    public void calculaTotais(int linhaAtual, double area, double mesJan, double mesFev, double mesMar, double mesAbr, double mesMai, double mesJun, double mesJul, double mesAgo, double mesSet, double mesOut, double mesNov, double mesDez, double verao, double outono, double inverno, double primavera){
        //Toast.makeText(this, "Linha Atual: " + linhaAtual, Toast.LENGTH_SHORT).show();

         DecimalFormat doisDecimais = new DecimalFormat("#.##");
        //Testa o tamanho do array com o numero de linha
        //Entra no if quando o botao de adicionar linhas é pressionado.
        if(listaDeAreas.size()< numeroDeLinhas && listaJan.size() < numeroDeLinhas && listaFev.size() < numeroDeLinhas && listaMar.size() < numeroDeLinhas && listaAbr.size() < numeroDeLinhas && listaMai.size() < numeroDeLinhas && listaJun.size() < numeroDeLinhas && listaJul.size() < numeroDeLinhas && listaAgo.size() < numeroDeLinhas && listaSet.size() < numeroDeLinhas && listaOut.size() < numeroDeLinhas && listaNov.size() < numeroDeLinhas && listaDez.size() < numeroDeLinhas && listaVerao.size() < numeroDeLinhas && listaOutono.size() < numeroDeLinhas && listaInverno.size() < numeroDeLinhas && listaPrimavera.size() < numeroDeLinhas){
            listaDeAreas.add(0.0);
            listaJan.add(0.0);
            listaFev.add(0.0);
            listaMar.add(0.0);
            listaAbr.add(0.0);
            listaMai.add(0.0);
            listaJun.add(0.0);
            listaJul.add(0.0);
            listaAgo.add(0.0);
            listaSet.add(0.0);
            listaOut.add(0.0);
            listaNov.add(0.0);
            listaDez.add(0.0);
            listaVerao.add(0.0);
            listaOutono.add(0.0);
            listaInverno.add(0.0);
            listaPrimavera.add(0.0);
        }
        else{
            //LinhaAtual = -2 quando o botão de remover linha é pressionado.
            if(linhaAtual != -2) {
                //linhaAtual+1 pois a primeira posição do array é 0 e a primeira linhaAtual é -1.
                listaDeAreas.set(linhaAtual + 1, area);
                listaJan.set(linhaAtual + 1, mesJan);
                listaFev.set(linhaAtual + 1, mesFev);
                listaMar.set(linhaAtual + 1, mesMar);
                listaAbr.set(linhaAtual + 1, mesAbr);
                listaMai.set(linhaAtual + 1, mesMai);
                listaJun.set(linhaAtual + 1, mesJun);
                listaJul.set(linhaAtual + 1, mesJul);
                listaAgo.set(linhaAtual + 1, mesAgo);
                listaSet.set(linhaAtual + 1, mesSet);
                listaOut.set(linhaAtual + 1, mesOut);
                listaNov.set(linhaAtual + 1, mesNov);
                listaDez.set(linhaAtual + 1, mesDez);

                listaVerao.set(linhaAtual + 1, verao);
                listaOutono.set(linhaAtual + 1, outono);
                listaInverno.set(linhaAtual + 1, inverno);
                listaPrimavera.set(linhaAtual + 1, primavera);
            }
        }

        //Area total Pastagem
        double somaDasAreas = 0.0;
        for(int i=0; i<listaDeAreas.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaDasAreas = somaDasAreas + listaDeAreas.get(i);
        }

        TextView totalHa = findViewById(R.id.tv_AreaTotalNum);
        totalHa.setText(String.valueOf(somaDasAreas)+ "ha");

        //Mes janeiro
        somaJan = 0.0;
        for(int i=0; i<listaJan.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));

             somaJan = somaJan + listaJan.get(i);
        }

        TextView totalJan = findViewById(R.id.tv_AreaTotalMesJan);
        totalJan.setText(String.valueOf(doisDecimais.format(somaJan)));


        //Mes fevereiro
         somaFev = 0.0;
        for(int i=0; i<listaFev.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaFev = somaFev + listaFev.get(i);
        }

        TextView totalFev = findViewById(R.id.tv_AreaTotalMesFev);
        totalFev.setText(String.valueOf(doisDecimais.format(somaFev)));


        //Mes março
         somaMar = 0.0;
        for(int i=0; i<listaMar.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaMar = somaMar + listaMar.get(i);
        }

        TextView totalMar = findViewById(R.id.tv_AreaTotalMesMar);
        totalMar.setText(String.valueOf(doisDecimais.format(somaMar)));
//
//        //Mes abril
         somaAbr = 0.0;
        for(int i=0; i<listaAbr.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaAbr = somaAbr + listaAbr.get(i);
        }

        TextView totalAbr = findViewById(R.id.tv_AreaTotalMesAbr);
        totalAbr.setText(String.valueOf(doisDecimais.format(somaAbr)));
//
//
//        //Mes MAIO
         somaMai = 0.0;
        for(int i=0; i<listaMai.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaMai = somaMai + listaMai.get(i);
        }

        TextView totalMai = findViewById(R.id.tv_AreaTotalMesMai);
        totalMai.setText(String.valueOf(doisDecimais.format(somaMai)));
//
//        //Mes junho
         somaJun = 0.0;
        for(int i=0; i<listaJun.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaJun = somaJun + listaJun.get(i);
        }

        TextView totalJun = findViewById(R.id.tv_AreaTotalMesJun);
        totalJun.setText(String.valueOf(doisDecimais.format(somaJun)));

//        //Mes julho Jul
         somaJul = 0.0;
        for(int i=0; i<listaJul.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaJul = somaJul + listaJul.get(i);
        }

        TextView totalJul = findViewById(R.id.tv_AreaTotalMesJul);
        totalJul.setText(String.valueOf(doisDecimais.format(somaJul)));
//
//        //Mes agosto
         somaAgo = 0.0;
        for(int i=0; i<listaAgo.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaAgo = somaAgo + listaAgo.get(i);
        }

        TextView totalAgo = findViewById(R.id.tv_AreaTotalMesAgo);
        totalAgo.setText(String.valueOf(doisDecimais.format(somaAgo)));
//
//        //Mes setembro
         somaSet = 0.0;
        for(int i=0; i<listaSet.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaSet = somaSet + listaSet.get(i);
        }

        TextView totalSet = findViewById(R.id.tv_AreaTotalMesSet);
        totalSet.setText(String.valueOf(doisDecimais.format(somaSet)));
//
//        //Mes outubro Out
         somaOut = 0.0;
        for(int i=0; i<listaOut.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaOut = somaOut + listaOut.get(i);
        }

        TextView totalOut = findViewById(R.id.tv_AreaTotalMesOut);
        totalOut.setText(String.valueOf(doisDecimais.format(somaOut)));
//
//        //Mes novembro
         somaNov = 0.0;
        for(int i=0; i<listaNov.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaNov = somaNov + listaNov.get(i);
        }

        TextView totalNov = findViewById(R.id.tv_AreaTotalMesNov);
        totalNov.setText(String.valueOf(doisDecimais.format(somaNov)));
//
//        //Mes dezembro Dez
         somaDez = 0.0;
        for(int i=0; i<listaDez.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaDez = somaDez + listaDez.get(i);
        }

        TextView totalDez = findViewById(R.id.tv_AreaTotalMesDez);
        totalDez.setText(String.valueOf(doisDecimais.format(somaDez)));


       //Verao
        double somaVer = 0.0;
        for(int i=0; i<listaVerao.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaVer = somaVer + listaVerao.get(i);
        }

        TextView totalVer = findViewById(R.id.tv_AreaTotalVer);
        totalVer.setText(String.valueOf(doisDecimais.format(somaVer)));




       //Outono
        double somaOutono = 0.0;
        for(int i=0; i<listaOutono.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaOutono = somaOutono + listaOutono.get(i);
        }

        TextView totalOutono = findViewById(R.id.tv_AreaTotalOut);
        totalOutono.setText(String.valueOf(doisDecimais.format(somaOutono)));





        //Inverno
        double somaInverno = 0.0;
        for(int i=0; i<listaInverno.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaInverno = somaInverno + listaInverno.get(i);
        }

        TextView totalInverno = findViewById(R.id.tv_AreaTotalInve);
        totalInverno.setText(String.valueOf(doisDecimais.format(somaInverno)));



        //Primavera
        double somaPrimavera = 0.0;
        for(int i=0; i<listaPrimavera.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaPrimavera = somaPrimavera + listaPrimavera.get(i);
        }

        TextView totalPrimavera = findViewById(R.id.tv_AreaTotalPrim);
        totalPrimavera.setText(String.valueOf(doisDecimais.format(somaPrimavera)));


    }

    /**
     * Método chamado toda vez que o botão Próximo Passo é clicado, e tem como objetivo levar o usário para outra activity e mandar um array de totais/mês. (Obs: método é acionado, no onclick do Botao Proximo Passo, que se encontra em activity_piquete.xml)
     * @param view
     */
    public void irParaAnimaisActivity(View view) {
        double somJan = somaJan, somFev = somaFev, somMar = somaMar, somAbr = somaAbr, somMai = somaMai, somJun = somaJun, somJul = somaJul, somAgo = somaAgo, somSet = somaSet, somOut = somaOut, somNov = somaNov, somDez = somaDez;

        //Bundle serve  basicamente para passar dados entre Activities.
        Bundle enviaValores = new Bundle();
        enviaValores.putDoubleArray("Valores totais/mês Ha", new double[]{somJan, somFev, somMar, somAbr, somMai, somJun, somJul, somAgo, somSet, somOut, somNov, somDez});
        Intent i=new Intent(getApplicationContext(), AnimaisActivity.class);
        i.putExtras(enviaValores);
        startActivity(i);

    }

}
