package com.example.testetelas1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        Intent intent = new Intent(MainActivity.this, Perfil.class);
        startActivity(intent);
    }




}
