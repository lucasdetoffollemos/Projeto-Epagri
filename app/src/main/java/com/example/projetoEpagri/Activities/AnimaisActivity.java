package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

                //Condiçao feita pra qunado digitar multiplas vezes no bota menos, e as linhas ja estiverem acabado, o botao continuar funcionando
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
        ArrayList<String> categoriaAnimal = new ArrayList<>();
        categoriaAnimal.add("BEZERROS");
        categoriaAnimal.add("NOVILHOS JOVENS");
        categoriaAnimal.add("NOVILHOS ADULTOS");
        categoriaAnimal.add("BEZERRAS");
        categoriaAnimal.add("NOVILHAS JOVENS");
        categoriaAnimal.add("NOVILHAS ADULTAS");
        categoriaAnimal.add("VACAS (MATRIZES)");
        categoriaAnimal.add("TOUROS");

        //Localiza o spinner no arquivo xml tabela_oferta_demanda_linha.
        Spinner spinnerCategoria = linha_tabela.findViewById(R.id.spinnerCategoria);
        //Cria um ArrayAdpter usando o array de string com categoriaAnimal. //Cria um ArrayAdapter que pega o Array de string e transforma em um spinner.
        ArrayAdapter<String> spinnerCategoriaAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, categoriaAnimal);
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
        ArrayAdapter<String> spinnerMesesAdapter = new ArrayAdapter<String>(AnimaisActivity.this, android.R.layout.simple_spinner_item, arrayMeses){
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//
//                View v = super.getDropDownView(position, convertView, parent);
//
//                ((TextView) v).setGravity(Gravity.CENTER);
//
//                return v;
//
//            }
        };
        spinnerMesesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(spinnerMesesAdapter);


        // Define uma tag para cada linha da tabela.
        linha_tabela.setTag(i);

        //Adicionando as Linhas da tabela no layout da tabela
        table_layout.addView(linha_tabela);

    }
}
