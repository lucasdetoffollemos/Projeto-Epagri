package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.animation.IntArrayEvaluator;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


import com.example.projetoEpagri.Classes.DadosSul;
import com.example.projetoEpagri.Dao.DadosSulDAO;
import com.example.projetoEpagri.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PiqueteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private  Button bt_adicionar_linha, bt_remover_linha, bt_resultado_cond;
    public int i=0;
    private  TableRow linha_tabela;
    private TableLayout table_layout;
    private DadosSulDAO dadosSulDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        dadosSulDAO = new DadosSulDAO(PiqueteActivity.this);

        table_layout = (TableLayout) findViewById(R.id.table_layout);

        bt_resultado_cond = findViewById(R.id.bt_resultado_conds);
        bt_resultado_cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraResultadoCond();
            }
        });

        bt_adicionar_linha = findViewById(R.id.bt_adicionar_linha);
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                linha_tabela = (TableRow) View.inflate(PiqueteActivity.this, R.layout.tabela_oferta_atual_linha, null);

                ArrayList<String> tipoPique1 = dadosSulDAO.getTiposPastagem();

                String [] condPique1 = new String[]{" ", "Degradada", "Média", "Ótima"};


//
//                for (int i= 0; i < condPique1.length; i++) {
//
//                    Toast.makeText(PiqueteActivity.this, "Clicado " + condPique1[i], Toast.LENGTH_SHORT).show();
//
//                }


//




                //Configuraçao de spinner para o Tipo de pastagem/piquetes
                //Criado um arquivo xml localizado res/values/arraysInfoPiquetes
                Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinnerTipoPiquete);
                Spinner spinnerCondPiquete = linha_tabela.findViewById(R.id.spinnerCondPiquete);

                //Criando arrayAdapter para o Tipo do piquete
                ArrayAdapter<String> spinnerArrayAdapterTipo = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, tipoPique1);
                spinnerArrayAdapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinnerTipoPiquete.setAdapter(spinnerArrayAdapterTipo);



                ////Criando arrayAdapter para a condiçao do piquete
                ArrayAdapter<String> spinnerArrayAdapterCond = new ArrayAdapter<>(PiqueteActivity.this, android.R.layout.simple_spinner_item, condPique1);
                spinnerArrayAdapterCond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCondPiquete.setAdapter(spinnerArrayAdapterCond);




                //Aplicando os método de seleçao para o spinner de tipo
                spinnerTipoPiquete.setOnItemSelectedListener(PiqueteActivity.this);


                //Aplicando os método de seleçao para o spinner de condiçao
                spinnerCondPiquete.setOnItemSelectedListener(PiqueteActivity.this);




                 // Setando uma tag para cada linha da tabela
                //linha_tabela.setTag(i);
                linha_tabela.setId(i);
                int id = linha_tabela.getId();
                i++;
                // Adicionando as Linhas da tabela no layout da tabela
                table_layout.addView(linha_tabela);
                Toast.makeText(PiqueteActivity.this, "Linha id "+id, Toast.LENGTH_SHORT).show();
            }
        });

        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(i));

                if(i == 0){
                    i = i;
                }

                else{
                    i--;
                }

            }
        });

    }

    /**
     * Métodos criados em funçao do implements AdapterView.OnItemSelectedListener, esses métodos sao utilizados para manipular os elementos dos spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           // String text = parent.getItemAtPosition(position).toString();
           //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();



    }

    /**
     * Métodos criados em funçao do implements AdapterView.OnItemSelectedListener, esses métodos sao utilizados para manipular os elementos dos spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void mostraResultadoCond(){
//
//       Double resultadoCond = dadosSulDAO.getCondicao();


       //linha_tabela.setId(ViewCompat.generateViewId());
        Toast.makeText(this, "Um texto", Toast.LENGTH_SHORT).show();
    }






}
