package com.example.projetoEpagri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class PiqueteActivity extends AppCompatActivity {

    String [] piquetesNome = { "Andropogon", "Angola", "Aveia Branca"};
    String [] piquetesCond = { "Degradada", "Média", "Ótima"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        Intent intent = getIntent();
        String nomePropEnviado = intent.getStringExtra(PropriedadeActivity.mensagem_extra);


        TextView setaNomeProp = findViewById(R.id.tv_titulo_piquetes);
        setaNomeProp.setText("Cadastre os piquetes da fazenda " + nomePropEnviado);

        ArrayAdapter<String> piquetesnomeArray = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, piquetesNome);
        int [] arrayIdsTipo = {R.id.dropdownNomePiquete, R.id.dropdownNomePiquete2};

        int sizeTipo = arrayIdsTipo.length;
        for(int i = 0; i < sizeTipo; i++) {
            MaterialBetterSpinner dropNomePiquete = findViewById(arrayIdsTipo[i]);
            dropNomePiquete.setAdapter(piquetesnomeArray);
        }


        ArrayAdapter<String> piquetesCondArray = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, piquetesCond);
        int [] arrayIdsCond = {R.id.dropdownCondPiquete, R.id.dropdownCondPiquete2};

        int sizeCond = arrayIdsCond.length;
        for(int i =0; i< sizeCond; i++){
            MaterialBetterSpinner dropCondPiquete = (MaterialBetterSpinner)findViewById(arrayIdsCond[i]);
            dropCondPiquete.setAdapter(piquetesCondArray);
        }




    }


}
