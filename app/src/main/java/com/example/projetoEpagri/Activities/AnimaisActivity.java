package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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


        //Nesta funçao quando selecionado algum item do spinner categoria de animais, ele seta o valor do consumo, para cada tipo de animal.
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long spin = spinnerCategoria.getSelectedItemId();


                if(spin == 0){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 2;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 1){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 2.5;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 2){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 3;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 3){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 2;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 4){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 2.5;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 5){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 3;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 6){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 3.0;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
                else if(spin == 7){
                    TextView tv_consumo = (TextView) linha_tabela.getChildAt(1);
                    double resultadoConsumo = 2.5;
                    tv_consumo.setText(String.valueOf(resultadoConsumo));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



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
        final Spinner spinnerMeses = (Spinner) linha.getChildAt(3);
        final EditText etPesoInicial = (EditText) linha.getChildAt(4);
        final EditText etPesoFinal = (EditText) linha.getChildAt(5);
        final EditText etPesoVer = (EditText) linha.getChildAt(6);
        final EditText etPesoOut = (EditText) linha.getChildAt(7);
        final EditText etPesoInv = (EditText) linha.getChildAt(8);
        final EditText etPesoPrim= (EditText) linha.getChildAt(9);


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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
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

                calculaPesoMedio(linha, meses, pesoInicialD, pesoFinalD, pesoVerD, pesoOutD, pesoInvD, pesoPrimD);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void calculaPesoMedio(final TableRow linha, String meses, double pesoInicial, double pesoFinal, double pesoVer, double pesoOut, double pesoIn, double pesoPrim){

        //Arredonda o cálculo para 2 decimais.
        DecimalFormat doisDecimais = new DecimalFormat("#.##");
        Double aproveitamento = 0.60;


        TextView janeiro = (TextView) linha.getChildAt(10);
        TextView fevereiro = (TextView) linha.getChildAt(11);
        TextView marco = (TextView) linha.getChildAt(12);
        TextView abril = (TextView) linha.getChildAt(13);
        TextView maio = (TextView) linha.getChildAt(14);
        TextView junho = (TextView) linha.getChildAt(15);
        TextView julho = (TextView) linha.getChildAt(16);
        TextView agosto = (TextView) linha.getChildAt(17);
        TextView setembro = (TextView) linha.getChildAt(18);
        TextView outubro = (TextView) linha.getChildAt(19);
        TextView novembro = (TextView) linha.getChildAt(20);
        TextView dezembro = (TextView) linha.getChildAt(21);



        if(meses == "Janeiro"){
            janeiro.setText(String.valueOf(1));
        }

        else {
            janeiro.setText(String.valueOf(0));
        }

        if(meses == "Fevereiro"){
            fevereiro.setText(String.valueOf(2));
        }
        else {
            fevereiro.setText(String.valueOf(0));
        }

        if(meses == "Março"){
            marco.setText(String.valueOf(3));
        }

        else{
            marco.setText(String.valueOf(0));
        }

        if(meses == "Abril"){
            abril.setText(String.valueOf(4));
        }

        else {
            abril.setText(String.valueOf(0));
        }

        if(meses == "Maio"){
            maio.setText(String.valueOf(5));
        }

        else {
            maio.setText(String.valueOf(0));
        }

        if(meses == "Junho"){
            junho.setText(String.valueOf(6));
        }

        else {
            junho.setText(String.valueOf(0));
        }

        if(meses == "Julho"){
            julho.setText(String.valueOf(7));
        }

        else {
            julho.setText(String.valueOf(0));
        }

        if(meses == "Agosto"){
            agosto.setText(String.valueOf(8));
        }

        else {
            agosto.setText(String.valueOf(0));
        }

        if(meses == "Setembro"){
            setembro.setText(String.valueOf(9));
        }

        else {
            setembro.setText(String.valueOf(0));
        }

        if(meses == "Outubro"){
            outubro.setText(String.valueOf(10));
        }

        else {
            outubro.setText(String.valueOf(0));
        }

        if(meses == "Novembro"){
           novembro.setText(String.valueOf(11));
        }

        else {
            novembro.setText(String.valueOf(0));
        }

        if(meses == "Dezembro"){
           dezembro.setText(String.valueOf(12));
        }

        else {
           dezembro.setText(String.valueOf(0));
        }

    }
}
