package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoEpagri.R;

import java.util.ArrayList;


public class AnimaisActivity extends AppCompatActivity {

    private Button bt_adicionar_linha, bt_remover_linha, bt_proximo_passo;
    public int i=-1, numeroDeLinhas=0;
    private TableRow linha_tabela;
    private TableLayout table_layout;

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

    }
}
