package com.example.testetelas1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =  findViewById(R.id.link);
       //textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                    enviaPerfil();
            }
        });
    }


    public void enviaPerfil(){
        Intent i = new Intent(MainActivity.this, Perfil.class);
        startActivity(i);
    }

//
//    public void enviaPerfil(View view) {
//        Intent intent = new Intent(MainActivity.this, Perfil.class);
//        startActivity(intent);
//    }
}
