package com.example.testetelas1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//Nesta classe é conectado o banco com o projeto, e aqui sao criados o banco, as tabelas e as colunas de cada tabela.
public class Conexao extends SQLiteOpenHelper {

    //Atributos de nome e versao do banco
    private static final  String name = "banco.db";
    private static final int version = 1;

    //Este metodo herda o nome e a versao do banco
    public Conexao(Context context) {
        super(context, name, null, version);
    }

    //Cria as tabelas do banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(id integer primary key autoincrement,  nome varchar(50), email varchar(100), telefone varchar(50), senha varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
