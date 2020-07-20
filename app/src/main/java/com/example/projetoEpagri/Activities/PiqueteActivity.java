package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetoEpagri.R;

public class PiqueteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);


    }
}


    /*
    String [] piquetesTipo = { "Andropogon", "Angola", "Aveia Branca"};
    String [] piquetesCond = { "Degradada", "Média", "Ótima"};


        I

        ArrayAdapter<String> piquetesnomeArray = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, piquetesTipo);
        MaterialBetterSpinner dropNomePiquete = findViewById(R.id.dropdownNomePiquete);
        dropNomePiquete.setAdapter(piquetesnomeArray);

        ArrayAdapter<String> piquetesCondArray = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, piquetesCond);
        MaterialBetterSpinner dropCondPiquete = (MaterialBetterSpinner)findViewById(R.id.dropdownCondPiquete);
        dropCondPiquete.setAdapter(piquetesCondArray);*/

        //int [] arrayIdsTipo = {R.id.dropdownNomePiquete, R.id.dropdownNomePiquete2};

        /*int sizeTipo = arrayIdsTipo.length;
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
}*/
