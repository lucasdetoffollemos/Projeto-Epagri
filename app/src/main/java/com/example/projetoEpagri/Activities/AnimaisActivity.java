package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class AnimaisActivity extends AppCompatActivity {

    private Button bt_adicionar_linha, bt_remover_linha, bt_finalizar_envio;
    public int i=-1, numeroDeLinhas=0;
    private TableRow linha_tabela;
    private TableLayout table_layout;
    private ArrayList<Double>  listaAnimais, listaJanUa, listaFevUa, listaMarUa, listaAbrUa, listaMaiUa, listaJunUa, listaJulUa, listaAgoUa, listaSetUa, listaOutUa, listaNovUa, listaDezUa;

    //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
    private String numAnimaisS, meses, pesoInicialS, pesoFinalS, pesoVerS, pesoOutS, pesoInvS, pesoPrimS;
    private double numAnimaisD, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD;
    private Spinner sp_meses;
    private EditText et_numAnimais, et_pesoInicial, et_pesoFinal, et_pesoVer, et_pesoOut, et_pesoInv, et_pesoPrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);
        listaAnimais = new ArrayList<>();
        listaJanUa = new ArrayList<>();
        listaFevUa = new ArrayList<>();
        listaMarUa= new ArrayList<>();
        listaAbrUa = new ArrayList<>();
        listaMaiUa = new ArrayList<>();
        listaJunUa = new ArrayList<>();
        listaJulUa = new ArrayList<>();
        listaAgoUa = new ArrayList<>();
        listaSetUa = new ArrayList<>();
        listaOutUa = new ArrayList<>();
        listaNovUa = new ArrayList<>();
        listaDezUa = new ArrayList<>();










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

        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               table_layout.removeView(table_layout.getChildAt(i));
                i--;

                //Este if serve pra quando é excluido uma linha, o valor do resultado ua/ha dimnuir, já que a linha contem valores que ajudam a aumentar no resultado.
                if(i >= -1){
                    listaAnimais.remove(numeroDeLinhas-1);
                    listaJanUa.remove(numeroDeLinhas-1);
                    listaFevUa.remove(numeroDeLinhas-1);
                    listaMarUa.remove(numeroDeLinhas-1);
                    listaAbrUa.remove(numeroDeLinhas-1);
                    listaMaiUa.remove(numeroDeLinhas-1);
                    listaJunUa.remove(numeroDeLinhas-1);
                    listaJulUa.remove(numeroDeLinhas-1);
                    listaAgoUa.remove(numeroDeLinhas-1);
                    listaSetUa.remove(numeroDeLinhas-1);
                    listaOutUa.remove(numeroDeLinhas-1);
                    listaNovUa.remove(numeroDeLinhas-1);
                    listaDezUa.remove(numeroDeLinhas-1);


                    calculoUaHa(-2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                }

                else{
                    i++;
                }
            }
        });


        bt_finalizar_envio = findViewById(R.id.bt_finalizar_envio);
        bt_finalizar_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaEnvio();
            }
        });

    }



    private void adicionarLinhaTabela(){
        //Infla a linha para a tabela
        linha_tabela = (TableRow) View.inflate(AnimaisActivity.this, R.layout.tabela_demanda_atual_linha, null);


        //Criando um array de String para as categorias de animais.
        final ArrayList<String> categoriaAnimal = new ArrayList<>();
        categoriaAnimal.add("BEZERROS");
        categoriaAnimal.add("NOVILHOS JOVENS");
        categoriaAnimal.add("NOVILHOS ADULTOS");
        categoriaAnimal.add("BEZERRAS");
        categoriaAnimal.add("NOVILHAS JOVENS");
        categoriaAnimal.add("NOVILHAS ADULTAS");
        categoriaAnimal.add("VACAS (MATRIZES)");
        categoriaAnimal.add("TOUROS");

        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        final Spinner spinnerCategoria = linha_tabela.findViewById(R.id.spinnerCategoria);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        final ArrayAdapter<String> spinnerCategoriaAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, categoriaAnimal);
        spinnerCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerCategoriaAdapter);






        //Criando um array de String para os meses.
        ArrayList<String> arrayMeses = new ArrayList<>();
        arrayMeses.add("Janeiro");
        arrayMeses.add("Fevereiro");
        arrayMeses.add("Março");
        arrayMeses.add("Abril");
        arrayMeses.add("Maio");
        arrayMeses.add("Junho");
        arrayMeses.add("Julho");
        arrayMeses.add("Agosto");
        arrayMeses.add("Setembro");
        arrayMeses.add("Outubro");
        arrayMeses.add("Novembro");
        arrayMeses.add("Dezembro");




        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        Spinner spinnerMeses = linha_tabela.findViewById(R.id.spinnerMeses);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        ArrayAdapter<String> spinnerMesesAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, arrayMeses);
        spinnerMesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(spinnerMesesAdapter);




        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(i);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);

        identiicaElementosLinha(linha_tabela);


    }

    //Setando os valores fora dos métodos, para serem levados com o objeto

    //Categoria
     String spinCategoria;

    //Consumo
    double resultadoConsumo;

    //Número de animais


    /**
     * Identifica os elementos da linha, dinamicamente, pelo seu index, e guarda os itens que foram selecionado, no spinner, ou armazenados no editText, para uso posterior
     * @param linha
     */
    public void identiicaElementosLinha(final TableRow linha){
        final Spinner spinnerCategoria= (Spinner) linha.getChildAt(0);
        final EditText etNumAnimais = (EditText) linha.getChildAt(2);
        final Spinner spinnerMeses = (Spinner) linha.getChildAt(3);
        final EditText etPesoInicial = (EditText) linha.getChildAt(4);
        final EditText etPesoFinal = (EditText) linha.getChildAt(5);
        final EditText etPesoVer = (EditText) linha.getChildAt(6);
        final EditText etPesoOut = (EditText) linha.getChildAt(7);
        final EditText etPesoInv = (EditText) linha.getChildAt(8);
        final EditText etPesoPrim= (EditText) linha.getChildAt(9);


        //Nesta funçao quando selecionado algum item do spinner categoria de animais, ele seta o valor do consumo, para cada tipo de animal.
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinCategoria = (String) spinnerCategoria.getSelectedItem();

                TextView tv_consumo = (TextView) linha.getChildAt(1);
                resultadoConsumo = 0;

                if(spinCategoria.equals("BEZERROS")){resultadoConsumo = 2;}

                    else if(spinCategoria.equals("NOVILHOS JOVENS")){resultadoConsumo = 2.5;}

                else if(spinCategoria.equals("NOVILHOS ADULTOS")){ resultadoConsumo = 3; }

                else if(spinCategoria.equals("BEZERRAS")){ resultadoConsumo = 2;}

                else if (spinCategoria.equals("NOVILHAS JOVENS")){ resultadoConsumo = 2.5; }

                else if(spinCategoria.equals("NOVILHAS ADULTOS")){ resultadoConsumo = 3; }

                else if(spinCategoria.equals("VACAS (MATRIZES)")){ resultadoConsumo = 3; }

                else if(spinCategoria.equals("TOUROS")){ resultadoConsumo = 2.5; }

                tv_consumo.setText(String.valueOf(resultadoConsumo));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //EditText numero de animais
        etNumAnimais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }

                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //Spinner meses
        spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }

                //meses
                sp_meses = spinnerMeses;
                meses = spinnerMeses.getSelectedItem().toString();



                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Edit Text Peso Inicial
        etPesoInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text Peso Final
        etPesoFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text Peso Verao
        etPesoVer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit text Peso Outono
        etPesoOut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit text Peso inverno
        etPesoInv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Edit Text peso Primavera
        etPesoPrim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Numero animais
                et_numAnimais = etNumAnimais;
                numAnimaisS = et_numAnimais.getText().toString();

                if(numAnimaisS.length()> 0){
                    numAnimaisD = Double.parseDouble(numAnimaisS);
                }

                else {
                    numAnimaisD = 0;
                }
                //meses
                sp_meses = spinnerMeses;
                meses = sp_meses.getSelectedItem().toString();

                //peso inicial
                et_pesoInicial = etPesoInicial;
                pesoInicialS = et_pesoInicial.getText().toString();

                if(pesoInicialS.length() > 0) {
                    pesoInicialD = Double.parseDouble(pesoInicialS);
                }
                else{
                    pesoInicialD = 0;
                }

                //peso final
                et_pesoFinal = etPesoFinal;
                pesoFinalS = et_pesoFinal.getText().toString();

                if(pesoFinalS.length() > 0) {
                    pesoFinalD = Double.parseDouble(pesoFinalS);
                }
                else{
                    pesoFinalD = 0;
                }

                //peso verao

                et_pesoVer = etPesoVer;
                pesoVerS = et_pesoVer.getText().toString();

                if(pesoVerS.length() > 0) {
                    pesoVerD = Double.parseDouble(pesoVerS);
                }
                else{
                    pesoVerD = 0;
                }

                //peso outono

                et_pesoOut = etPesoOut;
                pesoOutS = et_pesoOut.getText().toString();

                if(pesoOutS.length() > 0) {
                    pesoOutD = Double.parseDouble(pesoOutS);
                }
                else{
                    pesoOutD = 0;
                }
                //peso inverno
                et_pesoInv = etPesoInv;
                pesoInvS = et_pesoInv.getText().toString();

                if(pesoInvS.length() > 0) {
                    pesoInvD = Double.parseDouble(pesoInvS);
                }
                else{
                    pesoInvD = 0;
                }

                //peso primavera
                et_pesoPrim = etPesoPrim;
                pesoPrimS = et_pesoPrim.getText().toString();

                if(pesoPrimS.length() > 0) {
                    pesoPrimD = Double.parseDouble(pesoPrimS);
                }
                else{
                    pesoPrimD = 0;
                }

                if(meses != "" && pesoInicialD != 0 && pesoFinalD != 0 && pesoVerD != 0 && pesoOutD != 0 && pesoInvD != 0 && pesoPrimD != 0) {
                    calculosActivity(linha, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }




    //Setando array de peso médio dos meses para  usar no Objeto
    double [] arrayPesoMedioMes = new double[12];
    /**
     * Faz toda a parte, que envolve cálculos na tabela desta Activity, cálculos como peso médio, UA/ha, etc...
     * @param linha
     * @param meses
     * @param pesoInicial
     * @param pesoFinal
     * @param pesoVer
     * @param pesoOut
     * @param pesoInv
     * @param pesoPrim
     */
    public void calculosActivity(final TableRow linha, double numeroAnimais, String meses, double pesoInicial, double pesoFinal, double pesoVer, double pesoOut, double pesoInv, double pesoPrim){

        double ganho = 0;
        double [] ganhoEstacao = new double[]{pesoVer, pesoVer, pesoOut, pesoOut, pesoOut, pesoInv, pesoInv, pesoInv, pesoPrim, pesoPrim, pesoPrim, pesoVer};
        int posicao = 0;



        switch (meses){
            case "Janeiro":
                    posicao = 0;
                break;

            case "Fevereiro":
                posicao = 1;
                break;

            case "Março":
            posicao = 2;
            break;

            case "Abril":
                posicao = 3;
                break;

            case "Maio":
                posicao = 4;
                break;

            case "Junho":
                posicao = 5;
                break;

            case "Julho":
                posicao = 6;
                break;

            case "Agosto":
                posicao = 7;
                break;

            case "Setembro":
                posicao = 8;
                break;

            case "Outubro":
                posicao = 9;
                break;

            case "Novembro":
                posicao = 10;
                break;

            case "Dezembro":
                posicao = 11;
                break;

        }




            //ganho = (ganhoEstacao[posicao] * 30) /1000;


            //Estrutura de repetiçao feita para cada vez que o usuário trocar, o valor de entrada, os campos de textView de meses, limparem.
            for(int i = 10; i< 22; i++){
                TextView v = (TextView) linha.getChildAt(i);
                v.setText(String.valueOf(0));
            }




        //Estrutura de repetiçao feita, para gerar a sequencia de somas que resulta no peso final estipulado pelo usuário.\
        double  peso_atual, peso_agora[] = new double[12];

        for(peso_atual = pesoInicial; peso_atual < pesoFinal;){

            ganho = (ganhoEstacao[posicao] * 30) /1000;
            peso_atual = (peso_atual + ganho);

            if(posicao == 0){ peso_agora[0] = peso_atual;}
            else if(posicao == 1){ peso_agora[1] = peso_atual; }
            else if(posicao == 2){ peso_agora[2] = peso_atual;}
            else if(posicao == 3){ peso_agora[3] = peso_atual;}
            else if(posicao == 4){ peso_agora[4] = peso_atual;}
            else if(posicao == 5){ peso_agora[5] = peso_atual;}
            else if(posicao == 6){ peso_agora[6] = peso_atual;}
            else if(posicao == 7){ peso_agora[7] = peso_atual;}
            else if(posicao == 8){ peso_agora[8] = peso_atual;}
            else if(posicao == 9){ peso_agora[9] = peso_atual;}
            else if(posicao == 10){ peso_agora[10] = peso_atual;}
            else if(posicao == 11){ peso_agora[11] = peso_atual;}



            TextView v = (TextView) linha.getChildAt(posicao + 10);
            v.setText(String.valueOf(peso_atual));
            posicao++;

            if(posicao > 11){
                posicao = 0;
            }

        }


        double mesUaJan = peso_agora[0] * numeroAnimais;
        double mesUaFev = peso_agora[1] * numeroAnimais;
        double mesUaMar = peso_agora[2] * numeroAnimais;
        double mesUaAbr = peso_agora[3] * numeroAnimais;
        double mesUaMai = peso_agora[4] * numeroAnimais;
        double mesUaJun = peso_agora[5] * numeroAnimais;
        double mesUaJul = peso_agora[6] * numeroAnimais;
        double mesUaAgo = peso_agora[7] * numeroAnimais;
        double mesUaSet = peso_agora[8] * numeroAnimais;
        double mesUaOut = peso_agora[9] * numeroAnimais;
        double mesUaNov= peso_agora[10] * numeroAnimais;
        double mesUaDez= peso_agora[11] * numeroAnimais;



        arrayPesoMedioMes = new double[]{peso_agora[0], peso_agora[1], peso_agora[2], peso_agora[3], peso_agora[4], peso_agora[5], peso_agora[6], peso_agora[7], peso_agora[8], peso_agora[9], peso_agora[10], peso_agora[11]};


        calculoUaHa((Integer)linha.getTag(), numeroAnimais, mesUaJan, mesUaFev, mesUaMar, mesUaAbr, mesUaMai, mesUaJun, mesUaJul, mesUaAgo, mesUaSet, mesUaOut, mesUaNov, mesUaDez);
    }


    //Setando os valores fora dos métodos, para serem levados com o objeto
    double somaAnimal,  uaHaJanD, uaHaFevD, uaHaMarD, uaHaAbrD, uaHaMaiD, uaHaJunD, uaHaJulD, uaHaAgoD, uaHaSetD, uaHaOutD, uaHaNovD, uaHaDezD;
    double [] arrayUaHa = new double[12];


    /**
     * Método responsável por setar os valores dos cáculos ua/ha, no seus respectivos lugares
     * @param linhaAtual
     * @param qtde_animais
     * @param jan
     * @param fev
     * @param mar
     * @param abr
     * @param mai
     * @param jun
     * @param jul
     * @param ago
     * @param set
     * @param out
     * @param nov
     * @param dez
     */
    public void calculoUaHa(int linhaAtual, double qtde_animais, double jan, double fev, double mar, double abr, double mai, double jun, double jul, double ago, double set, double out, double nov, double dez){

        DecimalFormat doisDecimais = new DecimalFormat("#.##");

        if( listaAnimais.size()< numeroDeLinhas&& listaJanUa.size()< numeroDeLinhas && listaFevUa.size()< numeroDeLinhas && listaMarUa.size()< numeroDeLinhas && listaAbrUa.size()< numeroDeLinhas && listaMaiUa.size()< numeroDeLinhas && listaJunUa.size()< numeroDeLinhas && listaJulUa.size()< numeroDeLinhas && listaAgoUa.size()< numeroDeLinhas && listaSetUa.size()< numeroDeLinhas && listaOutUa.size()< numeroDeLinhas && listaNovUa.size()< numeroDeLinhas && listaDezUa.size()< numeroDeLinhas ){
            listaAnimais.add(0.0);
            listaJanUa.add(0.0);
            listaFevUa.add(0.0);
            listaMarUa.add(0.0);
            listaAbrUa.add(0.0);
            listaMaiUa.add(0.0);
            listaJunUa.add(0.0);
            listaJulUa.add(0.0);
            listaAgoUa.add(0.0);
            listaSetUa.add(0.0);
            listaOutUa.add(0.0);
            listaNovUa.add(0.0);
            listaDezUa.add(0.0);
        }

        else{
            if(linhaAtual != -2){
                listaAnimais.set(linhaAtual + 1, qtde_animais);
                listaJanUa.set(linhaAtual + 1, jan);
                listaFevUa.set(linhaAtual + 1, fev);
                listaMarUa.set(linhaAtual + 1, mar);
                listaAbrUa.set(linhaAtual + 1, abr);
                listaMaiUa.set(linhaAtual + 1, mai);
                listaJunUa.set(linhaAtual + 1, jun);
                listaJulUa.set(linhaAtual + 1, jul);
                listaAgoUa.set(linhaAtual + 1, ago);
                listaSetUa.set(linhaAtual + 1, set);
                listaOutUa.set(linhaAtual + 1, out);
                listaNovUa.set(linhaAtual + 1, nov);
                listaDezUa.set(linhaAtual + 1, dez);

            }
        }


        //Pegando os dados dos resultados de ha/mes, vindos da Activity Piquete
        Bundle b = this.getIntent().getExtras();
        double[] arrayUa = b.getDoubleArray("Valores totais/mês Ha");


        //quantidade de animais
        somaAnimal = 0.0;
        for(int i =0; i < listaAnimais.size(); i++){
            somaAnimal = somaAnimal + listaAnimais.get(i);
        }

        TextView quantidadeAnimal = findViewById(R.id.tv_totalAnimais);
        quantidadeAnimal.setText(String.valueOf(somaAnimal));

        //Mes janeiro
        double somaJan = 0.0;
        for(int i=0; i<listaJanUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaJan = (somaJan + listaJanUa.get(i));
        }

        double uaJan = somaJan /450;
        //Fazendo o cálculo de UA/HA, e setando nos respectivos TextViews

        uaHaJanD = uaJan/ arrayUa[0];
        String uaHaJan = doisDecimais.format(uaHaJanD);

        TextView totalJan = findViewById(R.id.tv_AreaUaMesJan);
        totalJan.setText((uaHaJan));





        //Mes fevereiro
        double somaFev = 0.0;
        for(int i=0; i<listaFevUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaFev = (somaFev + listaFevUa.get(i));
        }

        double uaFev = somaFev /450;
        uaHaFevD = uaFev/ arrayUa[1];
        String uaHaFev = doisDecimais.format(uaHaFevD);
        TextView totalFev = findViewById(R.id.tv_AreaUaMesFev);
        totalFev.setText(uaHaFev);


        //Mes março
        double somaMar = 0.0;
        for(int i=0; i<listaMarUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaMar = (somaMar + listaMarUa.get(i));
        }

        double uaMar = somaMar /450;
        uaHaMarD = uaMar/ arrayUa[2];
        String uaHaMar = doisDecimais.format(uaHaMarD);
        TextView totalMar = findViewById(R.id.tv_AreaUaMesMar);
        totalMar.setText(uaHaMar);


        //Mes Abril
        double somaAbr = 0.0;
        for(int i=0; i<listaAbrUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaAbr = (somaAbr + listaAbrUa.get(i));
        }
        double uaAbr = somaAbr /450;
        uaHaAbrD = uaAbr/ arrayUa[3];
        String uaHaAbr = doisDecimais.format(uaHaAbrD);
        TextView totalAbr = findViewById(R.id.tv_AreaUaMesAbr);
        totalAbr.setText(uaHaAbr);


        //Mes Maio
        double somaMaio = 0.0;
        for(int i=0; i<listaMaiUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaMaio= (somaMaio + listaMaiUa.get(i));
        }


        double uaMai = somaMaio /450;
        uaHaMaiD = uaMai/ arrayUa[4];
        String uaHaMai = doisDecimais.format(uaHaMaiD);
        TextView totalMaio = findViewById(R.id.tv_AreaUaMesMai);
        totalMaio.setText(uaHaMai);


        //Mes junho
        double somaJun = 0.0;
        for(int i=0; i<listaJunUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaJun = (somaJun + listaJunUa.get(i));
        }
        double uaJun = somaJun /450;
        uaHaJunD = uaJun/ arrayUa[5];
        String uaHaJun = doisDecimais.format(uaHaJunD);
        TextView totalJun = findViewById(R.id.tv_AreaUaMesJun);
        totalJun.setText(uaHaJun);

        //Mes julho
        double somaJul = 0.0;
        for(int i=0; i<listaJulUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaJul = (somaJul + listaJulUa.get(i));
        }
        double uaJul = somaJul /450;
        uaHaJulD = uaJul/ arrayUa[6];
        String uaHaJul = doisDecimais.format(uaHaJulD);
        TextView totalJul = findViewById(R.id.tv_AreaUaMesJul);
        totalJul.setText(uaHaJul);

        //Mes agosto
        double somaAgo = 0.0;
        for(int i=0; i<listaAgoUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaAgo = (somaAgo + listaAgoUa.get(i));
        }

        double uaAgo = somaAgo /450;
        uaHaAgoD = uaAgo/ arrayUa[7];
        String uaHaAgo = doisDecimais.format(uaHaAgoD);
        TextView totalAgo = findViewById(R.id.tv_AreaUaMesAgo);
        totalAgo.setText(uaHaAgo);

        //Mes setembro
        double somaSet = 0.0;
        for(int i=0; i<listaSetUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaSet = (somaSet + listaSetUa.get(i));
        }
        double uaSet = somaSet /450;
        uaHaSetD = uaSet/ arrayUa[8];
        String uaHaSet = doisDecimais.format(uaHaSetD);
        TextView totalSet = findViewById(R.id.tv_AreaUaMesSet);
        totalSet.setText(uaHaSet);

        //Mes Outubro
        double somaOut = 0.0;
        for(int i=0; i<listaOutUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaOut = (somaOut + listaOutUa.get(i));
        }
        double uaOut = somaOut /450;
        uaHaOutD = uaOut/ arrayUa[9];
        String uaHaOut = doisDecimais.format(uaHaOutD);
        TextView totalOut = findViewById(R.id.tv_AreaUaMesOut);
        totalOut.setText(uaHaOut);

        //Mes Novembro
        double somaNov = 0.0;
        for(int i=0; i<listaNovUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaNov = (somaNov + listaNovUa.get(i));
        }
        double uaNov = somaNov /450;
        uaHaNovD = uaNov/ arrayUa[10];
        String uaHaNov = doisDecimais.format(uaHaNovD);
        TextView totalNov = findViewById(R.id.tv_AreaUaMesNov);
        totalNov.setText(uaHaNov);

        //Mes Dezembro
        double somaDez = 0.0;
        for(int i=0; i<listaDezUa.size(); i++){
            //Log.i("LISTA AREA", ""+listaDeAreas.get(i));
            somaDez = (somaDez + listaDezUa.get(i));
        }
        double uaDez = somaDez /450;
        uaHaDezD = uaDez/ arrayUa[11];
        String uaHaDez = doisDecimais.format(uaHaDezD);
        TextView totalDez = findViewById(R.id.tv_AreaUaMesDez);
        totalDez.setText(uaHaDez);

        arrayUaHa = new double[]{uaHaJanD, uaHaFevD, uaHaMarD, uaHaAbrD, uaHaMaiD, uaHaJunD, uaHaJulD, uaHaAgoD, uaHaSetD, uaHaOutD, uaHaNovD, uaHaDezD};


    }

    private void finalizaEnvio() {


        Animais a = new Animais(spinCategoria, resultadoConsumo, numAnimaisD, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD,  arrayPesoMedioMes, somaAnimal, arrayUaHa);

        Intent intent = new Intent();
        intent.putExtra("Animais", a);
        setResult(RESULT_OK, intent);
        finish();
    }
}
