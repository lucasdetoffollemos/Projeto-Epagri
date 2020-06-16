package com.example.projetoEpagri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public TextView criaPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        criaPerfil =  findViewById(R.id.tv_criaPerfil);
       //textView.setMovementMethod(LinkMovementMethod.getInstance());

        criaPerfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                    enviaParaPerfil();
            }
        });
    }


    public void enviaParaPerfil(){
        Intent i = new Intent(MainActivity.this, Perfil.class);
        startActivity(i);
    }

//
//    public void enviaPerfil(View view) {
//        Intent intent = new Intent(MainActivity.this, Perfil.class);
//        startActivity(intent);
//    }
}
