package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.IntArrayEvaluator;
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

    private  Button bt_adicionar_linha, bt_remover_linha;
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

        bt_adicionar_linha = findViewById(R.id.bt_adicionar_linha);
        bt_adicionar_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linha_tabela = (TableRow) View.inflate(PiqueteActivity.this, R.layout.tabela_oferta_atual_linha, null);
                ArrayList<String> tipoPique1 = dadosSulDAO.getTiposPastagem();

//                Cursor cNames = db.function();
//                startManagingCursor(cNames);
//
//                // create an array to specify which fields we want to display
//                String[] from = new String[] { DBAdapter.KEY_NAME };
//                // create an array of the display item we want to bind our data to
//                int[] to = new int[] { android.R.id.text1 };
//
//                Spinner spinname =(Spinner)findViewById(R.id.name);
//                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//                        android.R.layout.simple_spinner_item, cNames, from, to);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinname.setAdapter(adapter);


                //Criando um ArrayAdpter usando um array de string e um spinner pré-definido no layout.
                //ArrayAdapter<CharSequence> adapterSpinnnerTipo = ArrayAdapter.createFromResource(PiqueteActivity.this, R.array.tipoPique, android.R.layout.simple_spinner_item);
                ArrayAdapter<CharSequence> adapterSpinnnerCond = ArrayAdapter.createFromResource(PiqueteActivity.this, R.array.condPique, android.R.layout.simple_spinner_item);
                //adapterSpinnnerTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterSpinnnerCond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Especificando o layout para usar quando a lista de escolha aparecer

                //Configuraçao de spinner para o Tipo de pastagem/piquetes
                //Criado um arquivo xml localizado res/values/arraysInfoPiquetes
                Spinner spinnerTipoPiquete = linha_tabela.findViewById(R.id.spinnerTipoPiquete);

                //O QUE EU MODIFIQUEI
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(PiqueteActivity.this, android.R.layout.simple_spinner_item, tipoPique1);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinnerTipoPiquete.setAdapter(spinnerArrayAdapter);
                //FIM

                // Aplicando o apapter para o spinner
                //spinnerTipoPiquete.setAdapter(adapterSpinnnerTipo);
                //Aplicando os método de seleçao para o spinner
                spinnerTipoPiquete.setOnItemSelectedListener(PiqueteActivity.this);

                //Configuraçao de spinner para o Condiçao de pastagem/piquetesLSD
                Spinner spinnerCondPiquete = linha_tabela.findViewById(R.id.spinnerCondPiquete);
                // Criando um ArrayAdpter usando um array de string e um spinner pré-definido no layout.
                spinnerCondPiquete.setAdapter(adapterSpinnnerCond);
                //Aplicando os método de seleçao para o spinner
                spinnerCondPiquete.setOnItemSelectedListener(PiqueteActivity.this);




                 // Setando uma tag para cada linha da tabela
                linha_tabela.setTag(i);
                i++;
                // Adicionando as Linhas da tabela no layout da tabela
                table_layout.addView(linha_tabela);

            }
        });






        bt_remover_linha = findViewById(R.id.bt_remover_linha);
        bt_remover_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_layout.removeView(table_layout.getChildAt(i));
                i--;
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

            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();


    }

    /**
     * Métodos criados em funçao do implements AdapterView.OnItemSelectedListener, esses métodos sao utilizados para manipular os elementos dos spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
