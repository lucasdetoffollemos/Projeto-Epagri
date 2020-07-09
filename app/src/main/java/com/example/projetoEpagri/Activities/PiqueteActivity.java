package com.example.projetoEpagri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.projetoEpagri.Classes.RecyclerViewAdapter;
import com.example.projetoEpagri.R;

import java.util.ArrayList;

public class PiqueteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition;
    String qtde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        Intent intent = getIntent();
        qtde = intent.getStringExtra("qtde_piquetes");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_piquete);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number);

        HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);


        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(PiqueteActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                /*if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    //Getting clicked value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    // Showing clicked item value on screen using toast message.
                    Toast.makeText(PiqueteActivity.this, Number.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                }*/

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    // function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList(){
        Number = new ArrayList<>();
        int qt = Integer.parseInt(qtde);

        for(int i=1; i<=qt; i++){
            Number.add("piquete_" + i);
        }
    }
    /*
    String [] piquetesTipo = { "Andropogon", "Angola", "Aveia Branca"};
    String [] piquetesCond = { "Degradada", "Média", "Ótima"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piquete);

        Intent intent = getIntent();
        String nomePropEnviado = intent.getStringExtra("nome_propriedade");

        TextView setaNomeProp = findViewById(R.id.tv_titulo_piquetes);
        setaNomeProp.setText("Cadastre os piquetes da fazenda " + nomePropEnviado);

        /*ArrayAdapter<String> piquetesnomeArray = new ArrayAdapter<String>
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
        }*/


        /*ArrayAdapter<String> piquetesCondArray = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, piquetesCond);
        int [] arrayIdsCond = {R.id.dropdownCondPiquete, R.id.dropdownCondPiquete2};

        int sizeCond = arrayIdsCond.length;
        for(int i =0; i< sizeCond; i++){
            MaterialBetterSpinner dropCondPiquete = (MaterialBetterSpinner)findViewById(arrayIdsCond[i]);
            dropCondPiquete.setAdapter(piquetesCondArray);
        }
    }*/
}
