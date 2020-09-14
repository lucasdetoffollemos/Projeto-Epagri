package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;


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

import com.example.projetoEpagri.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class AnimaisActivity extends AppCompatActivity {

    private Button bt_adicionar_linha, bt_remover_linha, bt_proximo_passo;
    public int i=-1, numeroDeLinhas=0;
    private TableRow linha_tabela;
    private TableLayout table_layout;

    //Declaração de atributos que são utilizados dentro da inner class (se não forem declarados, não tem acesso)
    private String meses, pesoInicialS, pesoFinalS, pesoVerS, pesoOutS, pesoInvS, pesoPrimS;
    private double pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD;
    private Spinner sp_meses;
    private EditText et_pesoInicial, et_pesoFinal, et_pesoVer, et_pesoOut, et_pesoInv, et_pesoPrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);


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


                if(i >= -1){
                }

                else{
                    i++;
                }
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

    public void identiicaElementosLinha(final TableRow linha){
        final Spinner spinnerCategoria= (Spinner) linha.getChildAt(0);
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
                long spin = spinnerCategoria.getSelectedItemId();

                TextView tv_consumo = (TextView) linha.getChildAt(1);
                double resultadoConsumo = 0;

                if(spin == 0){resultadoConsumo = 2;}

                else if(spin == 1){resultadoConsumo = 2.5;}

                else if(spin == 2){ resultadoConsumo = 3; }

                else if(spin == 3){ resultadoConsumo = 2;}

                else if(spin == 4){ resultadoConsumo = 2.5; }

                else if(spin == 5){ resultadoConsumo = 3; }

                else if(spin == 6){ resultadoConsumo = 3; }

                else if(spin == 7){ resultadoConsumo = 2.5; }

                tv_consumo.setText(String.valueOf(resultadoConsumo));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //Spinner meses
        spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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
                    calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void calculaPesoMedio(final TableRow linha, String meses, double pesoInicial, double pesoFinal, double pesoVer, double pesoOut, double pesoInv, double pesoPrim){

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




            ganho = (ganhoEstacao[posicao] * 30) /1000;

            for(int i = 10; i< 22; i++){
                TextView v = (TextView) linha.getChildAt(i);
                v.setText(String.valueOf(0));
            }

            for(double peso_atual = pesoInicial; peso_atual < pesoFinal;){

                ganho = (ganhoEstacao[posicao] * 30) /1000;
                peso_atual = (peso_atual + ganho);

                TextView v = (TextView) linha.getChildAt(posicao + 10);
                v.setText(String.valueOf(peso_atual));
                posicao++;

                if(posicao > 11){
                    posicao = 0;
                }

            }


    }
}
